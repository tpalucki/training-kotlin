package io.tpalucki.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

data class UserProfile(
    val userId: String,
    val details: String,
    val orders: List<String>,
)

suspend fun main() {
    CoroutineScopeFailFast().trigger()
}

class CoroutineScopeFailFast {
    suspend fun trigger() =
        // coroutineScope ensures fail-fast behavior: if one async fails,
        // the scope cancels the other async job automatically.
        coroutineScope {
            val userId = "user-${Random.nextInt()}"

            val detailsDeferred = async { fetchUserDetails(userId) }
            val ordersDeferred = async { fetchUserOrders(userId) }

            UserProfile(
                userId,
                details = detailsDeferred.await(),
                orders = ordersDeferred.await(),
            )
        }

    suspend fun fetchUserDetails(userId: String): String {
        delay(2000.milliseconds)
        return "All user details of user $userId"
    }

    suspend fun fetchUserOrders(userId: String): List<String> {
        delay(3000.milliseconds)
        return listOf("order1-$userId", "order2-$userId")
    }
}

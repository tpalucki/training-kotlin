package io.tpalucki.kotlin.language

fun main() {
    // data class is a class that is used to hold data. It automatically generates equals(), hashCode(), toString(), and copy() methods for you.
    val dataClass =
        UserProfile(
            userId = "user-123",
            details = "All user details of user user-123",
            orders = listOf("order1-user-123", "order2-user-123"),
        )

    // you can refer fields directly
    dataClass.userId
    // you can assign the new values dough if you use `var` instead of `val`
    dataClass.orders = listOf("order1-user-123")
    // you can't with `val`
//    dataClass.userId = "new userId"

    // auto generated methods
    dataClass.hashCode()
    dataClass.equals(null)
    dataClass.toString()
    // generic methods
    dataClass.component1() // userId
    dataClass.component2() // details
    dataClass.component3() // orders
}

data class UserProfile(
    val userId: String,
    var details: String,
    var orders: List<String>,
)

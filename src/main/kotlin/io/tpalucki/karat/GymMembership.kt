package io.tpalucki.karat

/*
We are building a program to manage a gym's membership. The gym has multiple members, each with a unique ID, name, and membership status. The program allows gym staff to add new members, update members status, and get membership statistics.

Definitions:
* A "member" is an object that represents a gym member. It has properties for the ID, name, and membership status.
* A "membership" is a class which is used for managing members in the gym.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for Membership is not passing due to a bug in the code. Make the necessary changes to Membership to fix the bug.
*/

/**
 * We are currently updating our system to include information about workouts for our members. As part of this update, we have introduced the Workout class, which represents a single workout session for a member.
 * Each object of the Workout class has a unique ID, as well as a start time and end time that are represented in the number of minutes spent from the start of the day. You can assume that all the Workouts are from the same day.
 * To implement these changes, we need to add two functions to the Membership class:
 *
 * 2.1) The `addWorkout` function takes a member ID and a `Workout` as inputs and associates the workout with that member. If the workout is associated successfully, the function should return `true`. If the given member does not exist while calling this function, the workout should be ignored and the function should return `false`.
 *
 * 2.2) The `getAverageWorkoutDurations` function should generate a map of member IDs onto their average workout durations in minutes. The returned map should include all members. If a member has no workouts, their value should be `null`.
 *
 * To assist you in testing these new functions, we have provided the testAddWorkout and testGetAverageWorkoutDurations functions.
 */
enum class MembershipStatus {
    /*
        Membership Status is of three types: BRONZE, SILVER and GOLD.
        BRONZE is the default membership a new member gets.
        SILVER and GOLD are paid memberships for the gym.
     */
    BRONZE,
    SILVER,
    GOLD,
}

class Workout(
    /**
     * This class represents a single workout session for a member.
     * Each object of the Workout class has a unique ID, as well as
     * a start time and end time that are represented in the number
     * of minutes spent from the start of the day.
     */

    private val id: Int,
    private val startTime: Int,
    private val endTime: Int,
) {
    fun getDuration(): Int = endTime - startTime
}

class Member(
    var memberId: Int,
    var name: String,
    var membershipStatus: MembershipStatus,
) {
    // Data about a gym member.

    override fun toString(): String = "Member ID: $memberId, Name: $name, Membership Status: $membershipStatus"
}

class Membership {
    /*
        Data for managing a gym membership, and methods which staff can
        use to perform any queries or updates.
     */
    var members: MutableList<Member> = mutableListOf()
    val membersWorkouts: MutableMap<Int, MutableList<Workout>> = mutableMapOf()

    fun addWorkout(
        memberId: Int,
        workout: Workout,
    ): Boolean {
        if (!membersWorkouts.contains(memberId)) {
            return false
        }

        membersWorkouts.computeIfAbsent(memberId) { mutableListOf() }.add(workout)
        return true
    }

    fun getAverageWorkoutDurations(): Map<Int, Int?> {
        val output = mutableMapOf<Int, Int?>()

        membersWorkouts.forEach { memberEntry ->
            val memberId = memberEntry.key
            val workouts = memberEntry.value
            if (workouts.isEmpty()) {
                output[memberId] = null
            } else {
                var durationsSum = 0
                for (workout in workouts) {
                    val duration = workout.getDuration()
                    durationsSum += duration
                }
                val averageDuration = durationsSum / workouts.size
                output[memberId] = averageDuration
            }
        }
        return output
    }

    fun addMember(member: Member) {
        members.add(member)
    }

    fun updateMembership(
        memberId: Int,
        membershipStatus: MembershipStatus,
    ) {
        for (member in members) {
            if (member.memberId == memberId) {
                member.membershipStatus = membershipStatus
                break
            }
        }
    }

    fun getMembershipStatistics(): MembershipStatistics {
        val totalMembers = members.size
        var totalPaidMembers = 0
        for (member in members) {
            if (member.membershipStatus in setOf(MembershipStatus.GOLD, MembershipStatus.SILVER)) {
                totalPaidMembers++
            }
        }
        val conversionRate = (totalPaidMembers.toDouble() / totalMembers) * 100.0
        return MembershipStatistics(totalMembers, totalPaidMembers, conversionRate)
    }
}

class MembershipStatistics(
    /*
        Class for returning the getMembershipStatistics result
     */
    var total_members: Int,
    var total_paid_members: Int,
    var conversion_rate: Double,
)

object SolutionKt {
    /*
        This is not a complete test suite, but tests some basic functionality of
        the code and shows how to use it.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        testMember()
        testMembership()
        testAddWorkout()
        testGetAverageWorkoutDurations()

        println("All test cases pass!")
    }

    fun testMember() {
        println("Running testMember")
        val testMember = Member(1, "John Doe", MembershipStatus.BRONZE)
        assert(testMember.memberId == 1)
        assert(testMember.name == "John Doe")
        assert(testMember.membershipStatus == MembershipStatus.BRONZE)
    }

    fun testMembership() {
        println("Running testMembership")
        val testMembership = Membership()
        val testMember = Member(1, "John Doe", MembershipStatus.BRONZE)
        testMembership.addMember(testMember)
        assert(testMembership.members.size == 1)
        assert(testMembership.members[0] == testMember)

        testMembership.updateMembership(1, MembershipStatus.SILVER)
        assert(testMembership.members[0].membershipStatus == MembershipStatus.SILVER)

        val testMember2 = Member(2, "Alex C", MembershipStatus.BRONZE)
        testMembership.addMember(testMember2)

        val testMember3 = Member(3, "Marie C", MembershipStatus.GOLD)
        testMembership.addMember(testMember3)

        val testMember4 = Member(4, "Joe D", MembershipStatus.SILVER)
        testMembership.addMember(testMember4)

        val testMember5 = Member(5, "June R", MembershipStatus.BRONZE)
        testMembership.addMember(testMember5)

        val testMember6 = Member(6, "Westley D", MembershipStatus.SILVER)
        testMembership.addMember(testMember6)

        val attendanceStats = testMembership.getMembershipStatistics()
        assert(attendanceStats.total_members == 6)
        assert(attendanceStats.total_paid_members == 4)
        assert(Math.abs(attendanceStats.conversion_rate - 66.67) < 0.1)
    }

    fun testAddWorkout() {
        println("Running testAddWorkout")
        val testMembership = Membership()
        val testMember1 = Member(12, "John Doe", MembershipStatus.SILVER)
        testMembership.addMember(testMember1)

        val testMember2 = Member(22, "Alex Cleeve", MembershipStatus.BRONZE)
        testMembership.addMember(testMember2)

        val testWorkout1 = Workout(111, 10, 20)
        val testWorkout2 = Workout(112, 15, 35)
        val testWorkout3 = Workout(113, 20, 25)
        val testWorkout99 = Workout(999, 1, 2)

        assert(testMembership.addWorkout(12, testWorkout1))
        assert(testMembership.addWorkout(22, testWorkout2))
        assert(testMembership.addWorkout(12, testWorkout3))
        assert(!testMembership.addWorkout(404, testWorkout99))
    }

    fun testGetAverageWorkoutDurations() {
        println("Running testGetAverageWorkoutDurations")
        val testMembership = Membership()
        val testMember1 = Member(12, "John Doe", MembershipStatus.SILVER)
        testMembership.addMember(testMember1)

        val testMember2 = Member(22, "Alex Cleeve", MembershipStatus.BRONZE)
        testMembership.addMember(testMember2)

        val testMember3 = Member(31, "Marie Cardiff", MembershipStatus.GOLD)
        testMembership.addMember(testMember3)

        val testMember4 = Member(37, "George Costanza", MembershipStatus.SILVER)
        testMembership.addMember(testMember4)

        val testWorkout1 = Workout(101, 10, 20)
        val testWorkout2 = Workout(102, 15, 35)
        val testWorkout3 = Workout(103, 45, 90)
        val testWorkout4 = Workout(104, 100, 155)
        val testWorkout5 = Workout(105, 120, 200)
        val testWorkout6 = Workout(106, 300, 400)
        val testWorkout7 = Workout(107, 1000, 1010)
        val testWorkout8 = Workout(108, 1010, 1045)

        testMembership.addWorkout(12, testWorkout1)
        testMembership.addWorkout(22, testWorkout2)
        testMembership.addWorkout(31, testWorkout3)
        testMembership.addWorkout(12, testWorkout4)
        testMembership.addWorkout(22, testWorkout5)
        testMembership.addWorkout(31, testWorkout6)
        testMembership.addWorkout(12, testWorkout7)
        testMembership.addWorkout(404, testWorkout8)

        val averageDurations = testMembership.getAverageWorkoutDurations()
        assert(Math.abs(averageDurations[12]!! - 25.0) < 0.1)
        assert(Math.abs(averageDurations[22]!! - 50.0) < 0.1)
        assert(Math.abs(averageDurations[31]!! - 72.5) < 0.1)
        assert(averageDurations.containsKey(37) && averageDurations[37] == null)
        assert(!averageDurations.containsKey(404))
    }
}

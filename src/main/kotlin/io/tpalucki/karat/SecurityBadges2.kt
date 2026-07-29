package io.tpalucki.karat

class SecurityBadges2 {
    /**
     * 🎯 Problem Description
     *
     * Given a list of badge records with timestamps formatted as 24-hour military time HHMM integers (e.g., "1354" -> 1354
     * or "830" -> 830),
     * find all employees who badged in 3 or more times within any 1-hour (60-minute) window.
     * 1-Hour Rule: The time difference between the first and last entry in a window must be <= 100 in military representation or <= 60 minutes.
     * Return a Map where the key is the employee name and the value is a list of all timestamps that form their longest/first matching 1-hour window.
     */
    fun findFrequentAccesses(badgeTimes: Array<Array<String>>): Map<String, List<Int>> {
        // gropup by employee employee to it's badges
        // sort for each employee
        // convert times to minutes
        // use sliding window to check each combination i to j to verfy if taht winfows is in 60 minutes

        val userToTimes = mutableMapOf<String, MutableList<Int>>()

        for (badgeTime in badgeTimes) {
            val user = badgeTime[0]
            val time = badgeTime[1]

            userToTimes.computeIfAbsent(user) { mutableListOf<Int>() }.add(time.toInt())
        }

        fun toMinutes(hhmm: Int): Int {
            val hours = hhmm / 100
            val minutes = hhmm % 100
            return hours * 60 + minutes
        }

        for ((user, times) in userToTimes) {
            times.sort()

            for (i in times.indices) {
                for (j in i until times.size) {
                    val windowStart = times[i]
                    val windowEnd = times[j]

                    // 3+ times in window
                    // time diff < 60

                    val windowInMinutes = toMinutes(windowEnd) - toMinutes(windowStart)

                    // j = 4
                    // i = 2

                    if (windowInMinutes <= 60 && j - i + 1 >= 3) {
                        val itemsInWindow = times.subList(i, j + 1)
                        userToTimes[user] = itemsInWindow
                        break
                    }
                }
            }
        }

        // return: map of employee to badges that match 1-hour window
        // todo
        return userToTimes.filter { it.value.size >= 3 }
    }
}

package io.tpalucki.karat

class AccessLogsAndSecurityBadges {
    /**
     * 🎯 Problem Description
     * We are given a list of security badge records where each entry contains a name and an action ("enter" or "exit").
     *
     * Write a function that returns two lists:
     *
     * - Employees who entered without exiting (entered, but didn't exit before their next entry or end of records).
     * - Employees who exited without entering (exited without a prior enter event).
     */
    fun findMismatchedBadges(badgeRecords: Array<Array<String>>): Pair<List<String>, List<String>> {
        val inside = mutableSetOf<String>()
        val outside = mutableSetOf<String>()

        val enteredWithoutExiting = mutableSetOf<String>()
        val exitedWithoutEntering = mutableSetOf<String>()

        for (badgeRecord in badgeRecords) {
            val currentEmployeeName = badgeRecord[0]
            val currentAction = badgeRecord[1]
            if (currentAction == "exit") {
                if (inside.contains(currentEmployeeName)) {
                    inside.remove(currentEmployeeName)
                } else {
                    exitedWithoutEntering.add(currentEmployeeName)
                }
            }

            if (currentAction == "enter") {
                if (outside.contains(currentEmployeeName)) {
                    outside.remove(currentEmployeeName)
                } else {
                    enteredWithoutExiting.add(currentEmployeeName)
                }
            }
        }

        // Anyone still inside at end of log failed to exit
        enteredWithoutExiting.addAll(inside)

        return Pair(enteredWithoutExiting.sorted().toList(), exitedWithoutEntering.sorted().toList())
    }

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
        val employeeToTimes = mutableMapOf<String, MutableList<Int>>()

        badgeTimes.forEach {
            val employee = it[0]
            val time = it[1]

            employeeToTimes.computeIfAbsent(employee) { mutableListOf() }.add(time.toInt())
        }

        // Step 2: Helper to convert HHMM to absolute minutes from midnight
        fun toMinutes(hhmm: Int): Int {
            val hours = hhmm / 100
            val minutes = hhmm % 100
            return hours * 60 + minutes
        }

        for ((name, times) in employeeToTimes) {
            times.sort()

            // for each employee iterate over sorted times
            // when time is more than 100, move windows

            for (i in times.indices) {
                for (j in i until times.size) {
                    val windowStart = times[i]
                    val windowsEnd = times[j]

                    val windowInMinutes = toMinutes(windowsEnd) - toMinutes(windowStart)

                    if (windowInMinutes <= 60 && (j - i + 1) >= 3) {
                        // Found a valid window
                        val windowTimes = times.subList(i, j + 1)
                        employeeToTimes[name] = windowTimes.toMutableList()
                        break
                        // for this window check
                    }
                }
            }
        }
        return employeeToTimes.filter { it.value.size >= 3 }
    }
}

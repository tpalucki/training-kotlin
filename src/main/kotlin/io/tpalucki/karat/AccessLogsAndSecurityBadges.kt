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
}

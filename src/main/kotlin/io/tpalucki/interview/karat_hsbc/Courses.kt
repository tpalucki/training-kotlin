package io.tpalucki.interview.karat_hsbc

class Courses {
    // [prereq, course]
// odd
    fun findMiddleCourse(prereqs: Array<Array<String>>): String {
        val prereqToCoure = mutableMapOf<String, String>()
        val prerequisities = mutableListOf<String>()
        val courses = mutableListOf<String>()

        // o(n)
        for (prereqAndCourse in prereqs) {
            // find path from start to end
            // find first course
            // bild path from first to last
            // find middle by list.size/2
            val prereq = prereqAndCourse[0]
            val course = prereqAndCourse[1]
            prereqToCoure[prereq] = course

            prerequisities.add(prereq)
            courses.add(course)
        }

        val start = (prerequisities - courses).first()
        val path = mutableListOf<String>()

        var current: String? = start
        while (current != null) {
            path.add(current)
            current = prereqToCoure[current]
        }

        val middleCourseIndex = path.size / 2
        return path[middleCourseIndex]
    }

// complexity
// runtime O(n)
// space complexity O(n)
}

package io.tpalucki

class Cheatsheet {
    fun a() {
        // set, list, map
        mutableSetOf<String>()
        mutableListOf<Int>()
        mutableMapOf<Int, MutableList<Int>>()

        // set
        val set = mutableSetOf<String>()
        set.add("hello")
        set.addAll(listOf("hello", "world"))
        set.remove("hello")
        set.removeIf { it.startsWith("h") }
        set.removeAll { it.startsWith("h") }

        // list
        val list = mutableListOf<Int>()
        list.add(1)
        list.addAll(listOf(1, 2, 3))
        list.remove(1)
        list.removeLast()
        list.removeFirst()
        list.removeIf { it % 2 == 0 }

        // map
        val map = mutableMapOf<Int, MutableList<Int>>()
        map[1] ?: 0
        map.computeIfAbsent(1) { mutableListOf() }.add(2)
        map.computeIfPresent(1) { key, v -> v.apply { add(3) } }
        map.getOrDefault(0, mutableListOf()).apply { add(4) }

        // stack
        val queue = ArrayDeque<Int>()
        queue.add(0)
        queue.addAll(listOf(1, 2, 3))
        queue.removeFirst()
        queue.removeLast()
        queue.remove(5)

        val partsList: List<String> = "yahoo.com".split(".")
    }
}

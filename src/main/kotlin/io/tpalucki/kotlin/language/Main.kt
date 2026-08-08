package io.tpalucki.kotlin.language

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments at Run/Debug configuration
    println("Program arguments: ${args.joinToString()}")
    println("Program arguments: " + args.contentToString())

//    val name = readLine()
//    println("Hello $name!")

    // Functions
    println(sum(1, 2))
    println(sumExp(1, 2))
    println(sumExpNoRet(1, 2))

    // Variables (Read-only)
    val a: Int = 1
    val b = 2
    val c: Int
    c = 3
//    c = 4; // we cannot reassign value to val variable

    // Variables (reassigned)
    var d = 4
//    d = "aa"; <- cannot change type
    d += 5 // you can reassign with the same type

    fun incrementD() { // you can create local function
        d += 3
    }
    incrementD()
    println("Incremented using function of top level d variable $d")

    // Classes and ingeritance ---> have a look at Shape and Rectangle classes

    // String templates
    var a1 = 1
    val s1 = "a is $a1" // simple replacement
    println(s1)

    a1 = 2
    val s2 = "${s1.replace("is", "was")}, but now is $a1"
    println(s2)

    // Conditional expressions
    maxOf(1, 2)
    maxOfV2(1, 2)

    // loops
    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }

    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    // while
    var index = 0
    while (index < items.size) {
        println("Item at $index is ${items[index]}")
        index++
    }

    whenTest(1)
    ranges()
}

fun sum(
    a: Int,
    b: Int,
): Int = a + b

fun sumExp(
    a: Int,
    b: Int,
): Int = a + b

fun sumExpNoRet(
    a: Int,
    b: Int,
) = a + b

fun printSum(
    a: Int,
    b: Int,
) {
    println("Sum of $a and $b is ${a + b}")
}

fun maxOf(
    a: Int,
    b: Int,
): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

fun maxOfV2(
    a: Int,
    b: Int,
): Int = if (a > b) a else b

fun whenTest(obj: Any): String =
    when (obj) {
        "1" -> "One"
        1 -> "One (Num)"
        is Long -> "Long"
        !is String -> "not String"
        else -> "Unknown"
    }

fun ranges() {
    val x = 10
    val y = 11

    if (x in 1..y) {
        println("Fits in range")
    }

    if (x - 100 !in 1..y * 2) {
        println("Out of range")
    }

    // iterate over a range
    for (x in 1..5) {
        println("Iterating over a range $x out of 1..5")
    }

    for (x in 2..10 step 2) {
        println("Iterating over a range $x out of 2..10 step 2")
    }
}

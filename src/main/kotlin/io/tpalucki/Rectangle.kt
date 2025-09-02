package io.tpalucki

//Properties can be listed in the body class declaration
class Rectangle(var height: Double, var length: Double): Shape() {
    var perimeter = (height + length) * 2
}

fun mainV2() {
    val rec = Rectangle(1.0, 2.0)
    println("Rectangle perimeter is ${rec.perimeter}")
}
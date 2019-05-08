package com.github.apycazo.playground.kotlin

class SimpleApp

fun main(args: Array<String>) {
  println ("- Simple app")
  println ("- Calling a function: '${returnOk()}'")
  println ("- Sum function: 3 + 5 = ${sum(3, 5)}")
  println ("- Increment using the default delta: increment(5) = ${increment(5)}")
  println ("- Increment using a custom delta: increment(5,2) = ${increment(5,  2)}")
  println ("- Function call using a named parameter: addDelta(5) = ${addDelta(value = 5)}")
  println ("- Value 3 is ${returnControlStructure(3)}")
  println ("- Value 8 is ${returnControlStructure(8)}")
  println ("- Control structure counter value is ${controlStructures()}")
  println ("- Joining mutable string list returns: ${joinValues()}")
  println ("- Is value returns: ${isNullValue(null)}")
  println ("- Offset 2 over 10 returns: ${offset(2).invoke(10)}")
  println ("- Data class get method: ${User("10", "john").name}")
  println ("- Data class get method: ${User("10").name}")
  vars()
}

// simple function syntax
fun returnOk(): String = "OK"

// more complex function syntax: a & b must not be null
fun sum(a: Int, b: Int): Int {
  return a + b
}

// function arguments can have default values
fun increment(value:Int, delta:Int = 1): Int {
  return value + delta
}

// when an argument with a default value precedes one without it, it must be called using the name
fun addDelta(delta:Int = 1, value:Int): Int {
  return value + delta
}

// control structures
fun controlStructures():Int {
  var count = 0
  for (i in 0..5) count += i
  println ("- Count becomes $count after first iteration")
  var x = 0
  val max = 5
  while (x++ < max) count++
  println ("- Count becomes $count after second iteration")
  val values = arrayOf(1,2,3)
  for (v in values) count += v
  println ("- Count becomes $count after third iteration")
  when {
    count % 2 == 0 -> println("- Final count is even")
    else -> {
      println ("- Final count is odd")
    }
  }
  return count
}

// variable types
fun vars() {
  // a read only variable uses syntax 'val'
  val max = 10
  // a variable uses syntax 'var'
  var i = 0
  // if not value is provided, the type is
  var count: Int
  count = 0
  while (i++ < max) count++
  println("- Count = $i")
}

// a return clause can be used on a structure
fun returnControlStructure(value:Int):String {
  return if (value % 2 == 0) "even" else "odd"
}

// mutable lists must be specified as such
fun joinValues(): String {
  val strings = mutableListOf("a", "b", "c")
  strings.add("d")
  return strings.joinToString(separator = ", ", prefix = "[", postfix = "]")
}

// null values must be specifically allowed with '?'
fun isNullValue(value: String?): Boolean {
  return value == null
}

// functions are first class and can be stored in variables and returned as objects
fun offset (delta: Int): (Int) -> Int {
  // as a lambda expression
  val increment = { a:Int -> a + delta }
  println ("- Lambda increment over 10 returns: ${increment(10)}")
  // an an anonymous function
  val decrement = fun(a:Int):Int { return a - delta }
  println ("- Anonymous decrement over 10 returns: ${decrement(10)}")
  // functions extended so they can be called over their receiver type
  val intPlus: Int.(Int) -> Int = Int::plus
  println ("- Call over receiver type with value 2: ${2.intPlus(10)}")
  // return a function to try invoking with invoke instead of ()
  return decrement
}

// data classes are simple!
data class User(val id:String, var name:String = "")
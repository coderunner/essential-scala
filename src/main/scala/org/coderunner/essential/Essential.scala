package org.coderunner.essential

import scala.annotation.tailrec

object Essential {

  // use 'val' to define values (i.e. immutable reference)
  // the type annotation follows the value name
  val title: String = "Essential Scala"
  val anotherTitle = "Type annotation can be omitted, the compiler will infer it."

  // use 'var' to define variables
  var counter: Int = 1
  counter = counter + 1

  //conditionals
  if(counter > 0) {
    //
  } else if (counter < 0) {
    //
  } else {
  }

  // define a function using the 'def' keyword
  // annotated the type of each parameter and of the return value
  // don't forget the '='
  def add(operand1: Int, operand2: Int) : Int = {
    // you don't need an explicit return
    // the result of the last expression is the value returned by the function
    operand1 + operand2
  }
  add(1, 2)

  // functions are objects and can be passed to functions
  // (Int) => Int is the type of a function that takes a Int and returns a Int
  def applyFunction(value: Int, f: (Int) => Int): Int = {
    f(value)
  }
  applyFunction(3, (v: Int) => v * 3)

  // function that do not return a value has a return type of Unit (aka void)
  def print(s: String): Unit = {
    println(s)
  }

  // a trait defines an abstract type
  // a trait can define attributes and methods
  // attributes and methods can be concrete or abstract (undefined)
  trait Apples {
    val color: String = "red" // concrete attribute
    def name: String // abstract method
    def print: String = { // concrete method
      name
    }
  }
  trait OtherTrait {}

  // class can extends traits
  // class constructor parameters are passed as class arguments
  // these values are also private members
  // notice the use of the 'with' keyword for subsequent traits
  class Spartan(bitterness: Int) extends Apples with OtherTrait {

    // any code in the body of the class is execute at construction
    // by default attributes and methods define in the class body have 'public' access
    private val awesomeness: Int = bitterness * 2

    // class need to override abstract members of be 'abstract'
    override def name: String = "spartan"
  }
  val myApple = new Spartan(2)
  myApple.name

  //objects are singleton
  //they are created lazily
  object TheApple extends Apples {
    override def name: String = "ze apple"
  }

  // case classes are class that exports there constructor parameters and can be use for pattern matching
  // case classes are often use for data objects
  case class User(name: String, age: Int)
  val john = User("john", 22) // for case classes the new operator can be omitted
  john.name
  john.age

  // case classes can be deconstructed to extract attributes
  val User(name, age) = john

  // 'match' can be used for pattern matching
  // Any is the root of all classes (think Java Object)
  def matching(p: Any) = {
    p match {
      case "a" => // matching on value
      case a: Apples => a.name // matching on type
      case User(n, a) if a > 10 => // matching on structure with condition
      case _ => // matches all
    }
  }

  // generic types are between '[]'
  def generic[T, U](arg: T, f: T => U): U = {
    f(arg)
  }
  def singletonList[T](item: T) : List[T] = {
    List(item)
  }
  val l_12: List[Int] = generic[Int, List[Int]](12, singletonList)

  // tuples can be created like the following
  // elements are accessed through the method _x where x is the position in the tuple (starting from 1)
  val trio: (String, Int, Apples) = ("rico", 2333, new Spartan(2))
  trio._1 //"rico"
  trio._2 //2333
  trio._3 //Spartan(2)

  //tuples can also be deconstructed
  val (k, v) = ("key", "value")

  //range
  val r1_10 = 1 to 10 // Range containing 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
  val r1_9 = 1 until 10 // Range containing 1, 2, 3, 4, 5, 6, 7, 8, 9

  //default Scala collection are immutable so modifications will return a new updated copy

  //sequence
  val oneTwoThree = Seq(1, 2, 3)

  //list
  val l = List(1, 2, 3)
  val prefixedList = 0 :: l

  //set
  val s = Set(1, 3, 4)
  val sPlus5 = s + 5

  //map
  val m = Map("k1" -> "v1", "k2" -> "v2")
  val mWithK3 = m + ("k3" -> "v3")
  m("k1")

  //collections have many useful methods
  l.filter((v: Int) => v < 2)
  l.map((v: Int) => v * 2)
  l.foreach((v: Int) => println(v))

  //for-loop
  //for each item in 'l' then for each element in 's' generate the product
  for(i <- l; e <- s) yield i * e
  //for each item in 'l' then for each element in 's' print the product
  for(i <- l; e <- s) {
    println(i * e)
  }

  //recursion
  def sumAllFrom(value: Int): Int = {
    //the compiler will check that the function is tail-recursive (last call is the recursive call)
    //so that it can be optimized to a loop
    //you want that to avoid stack overflow
    @tailrec
    def internalSum(value: Int, currentSum: Int): Int = {
      if (value == 0) {
        currentSum
      } else {
        internalSum(value - 1, currentSum + value)
      }
    }
    internalSum(value, 0)
  }
  sumAllFrom(4)
}

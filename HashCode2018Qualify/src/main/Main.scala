package main

import scala.collection.mutable

object Main extends App {

  println(solution(Array(), Array()))//0

  println(solution(Array(1,2), Array(3,4)))//2
  println(solution(Array(2,2), Array(0, 0)))//2
  println(solution(Array(1,2), Array(2,3)))//2
  println(solution(Array(1,2), Array(1,2)))//2

  println(solution(Array(2,2,2,2), Array(3,4,4,4)))//3

  println(solution(Array(1,2,3,4,5,6), Array(3,3,3,3,3,3)))//4
  println(solution(Array(2,2,2,2,2,2), Array(7,4,2,5,1,2)))//4

  println(solution(Array(1,1,2,2,3,3), Array(1,4,5,6,6,7)))//6

  println(solution(Array(1,1,1,1,2,3,4,5,6,1,1,8), Array(10,1,11,1,12,7,2,8,8,8,2,3)))//11


  def solution(a: Array[Int], b: Array[Int]): Int = {
    var coinsA = a.length / 2
    var coinsB = coinsA

    val setA = mutable.HashSet[Int]()
    val setB = mutable.HashSet[Int]()

    a.foreach{setA.add}
    b.foreach{setB.add}

    // Count how many unique types we have in A, in B, and how many duplicates across both
    // A = [1,2,3,4] and B = [3,3,3,7] would return:
    // uniqueInA = 3 (1,2 and 4 are only present in A)
    // uniqueInB = 1 (7 is only number unique in B)
    // duplicate = 1 (3 is the only number present in both)
    var uniqueInA = 0
    var uniqueInB = 0
    var duplicates = 0
    setA.foreach{ v =>
      if (!setB.contains(v)) {
        uniqueInA += 1
      } else {
        duplicates += 1
      }
    }

    setB.foreach{ v =>
      if (!setA.contains(v)) {
        uniqueInB += 1
      }
    }

    // Buy all unique candies
    var totalUniquePurchase = 0

    val amountPurchaseA = if (coinsA >= uniqueInA) uniqueInA else coinsA
    coinsA -= amountPurchaseA
    totalUniquePurchase += amountPurchaseA

    val amountPurchaseB = if (coinsB >= uniqueInB) uniqueInB else coinsB
    coinsB -= amountPurchaseB
    totalUniquePurchase += amountPurchaseB

    // setA and setB are equal and have only duplicated candies, just spend all coins
    var coins = coinsA + coinsB
    val amountPurchaseDup = if (coins >= duplicates) duplicates else coins
    coins -= amountPurchaseDup
    totalUniquePurchase += amountPurchaseDup

    totalUniquePurchase
  }

}



/*

  def solution(a: Array[Int], b: Array[Int]): Int = {
    if (a.length != b.length) return -1

    // Doubles handles how many doubles pieces we have of each number, like 5:5
    val doubles = mutable.HashMap[Int,Int]()
    // Singles handles how many single pieces we have of each number, like 1:2
    // Singles value can be at maximum 2, because we cannot use more than 2 single
    // pieces at the solution
    val singles = mutable.HashMap[Int,Int]()
    a.indices.foreach { i =>
      if (a(i) == b(i)) {
        doubles(a(i)) = doubles.getOrElseUpdate(a(i), 0) + 1
      } else {
        singles(a(i)) = Math.min(singles.getOrElseUpdate(a(i), 0) + 1, 2)
        singles(b(i)) = Math.min(singles.getOrElseUpdate(b(i), 0) + 1, 2)
      }
    }

    // Figure out the largest sequence. It will be the one who has the most doubles + singles
    0.to(6).map{ i =>
      singles.getOrElse(i, 0) + (doubles.getOrElse(i, 0) * 2)
    }.max
  }

 */

/*

 val open = -2
  val mine = -1
  val size = 10
  val mines = (size * size * 0.1).toInt
  var grid = Array.ofDim[Int](size, size)
  val rnd = new Random()
  0.until(mines).foreach(_ => grid(rnd.nextInt(size))(rnd.nextInt(size)) = mine)

  private def getNeighbours(row: Int, col: Int): List[(Int, Int)] = {
    var result = ListBuffer[(Int, Int)]()

    if(row > 0) result += ((row-1, col))
    if(row > 0 && col > 0) result += ((row-1, col-1))
    if(col > 0) result += ((row, col-1))
    if(row > 0 && col < size - 1) result += ((row-1, col+1))
    if(col < size - 1) result += ((row, col +1))
    if(row < size - 1 && col < size - 1) result += ((row+1, col+1))
    if(row < size -1) result += ((row+1, col))
    if(col > 0 && row < size - 1) result += ((row+1, col-1))

    result.toList
  }

  def click(row: Int, col: Int): Unit = {

    if (grid(row)(col) == mine) {
      Console.println("You lose")
    } else {
      var toVisit = mutable.Queue[(Int, Int)]()
      val visited = mutable.HashSet[(Int, Int)]()
      toVisit.enqueue((row, col))

      while(toVisit.nonEmpty) {
        val current = toVisit.dequeue()
        val neighbours = getNeighbours(current._1,current._2)
        var count = 0
        neighbours.foreach { n =>
          if (grid(n._1)(n._2) == mine) {
            count += 1
          }
          if (!visited.contains(n) && grid(n._1)(n._2) != mine) {
            toVisit.enqueue(n)
          }
        }
        grid(current._1)(current._2) = if (count == 0) open else count
        visited += current
      }
    }
  }

  def printGrid(): Unit = {
    0.until(size).foreach { r =>
      0.until(size).foreach { c =>
        val cur = grid(r)(c)
        val char = cur match {
          case 0 => "X"
          case -1 => "X"
          case -2 => "_"
          case x: Any => x
        }
        print(char)
      }
      println()
    }
  }

  while(true) {
    Console.print(printGrid())
    val line = Console.readLine()
    if (line == "quit") {
      System.exit(0)
    } else {
      val split = line.split(",")
      click(split(0).toInt, split(1).toInt)
    }

  }
 */


/*
  var size = 10
  var map = Array.ofDim[Int](size,size)
  var id = 0
  val islands = mutable.HashMap[Int, Int]()
  var count = 0


  Console.println(putLand(0,0)) // 1
  Console.println(putLand(1,1)) // 2
  Console.println(putLand(0,1)) // 1

  Console.println(putLand(0,3)) // 2
  Console.println(putLand(1,3)) // 2

  Console.println(putLand(3,3)) // 3
  Console.println(putLand(3,2)) // 3
  Console.println(putLand(3,1)) // 3

  Console.println(putLand(1,2)) // 2

  Console.println(putLand(2,2)) // 1

  def getNeighbours(row: Int, col: Int): List[Int] = {
    var result = ListBuffer[Int]()
    if (row > 0 && map(row-1)(col) != 0) result += map(row-1)(col)
    if (row < size - 1 && map(row+1)(col) != 0) result += map(row+1)(col)
    if (col > 0 && map(row)(col-1) != 0) result += map(row)(col-1)
    if (col < size - 1 && map(row)(col+1) != 0) result += map(row)(col+1)
    result.toList
  }

  def getUnique(neighbours: List[Int]): List[Int] = {
    val set = new mutable.HashSet[Int]()
    neighbours.foreach { n =>
      var cur = n
      while(islands(cur) != cur){
        cur = islands(cur)
      }
      set += cur
    }
    set.toList
  }

  def putLand(row: Int, col: Int): Int = {
    if (map(row)(col) == 0) {
      val neighbours = getNeighbours(row, col)
      if (neighbours.isEmpty) {
        id += 1
        map(row)(col) = id
        islands(id) = id
        count += 1
      } else {
        val uniqueNeighours = getUnique(neighbours)

        uniqueNeighours.tail.foreach { u =>
          islands(u) = uniqueNeighours.head
          count -= 1
        }
        map(row)(col) = uniqueNeighours.head
      }
    }
    count
  }
 */










/*
  val allFiles = List[String]("a_example.in", "b_should_be_easy.in", "c_no_hurry.in", "d_metropolis.in", "e_high_bonus.in")
//  val allFiles = List("a_example.in")
//  val allFiles = List("b_should_be_easy.in")
//  val allFiles = List("c_no_hurry.in")
//  val allFiles = List("d_metropolis.in")

  var totalScore = 0
  var maxTotalScore = 0

  var score = 0
  var maxScore = 0
  allFiles.foreach { file =>
    val (rows, columns, fleet, bonus, rides, steps) = InputReader.read(file)

    val vehicles = BasicSolver.solve(rows, columns, fleet, rides, steps, bonus)

    OutputWriter.write(file, vehicles)

    val tmpScore = Scorer.compute(bonus, rides, vehicles, steps)
    score += tmpScore
    val tmpMaxScore = Scorer.maxScorer(bonus, rides)
    maxScore += tmpMaxScore
    println(s"$file => $tmpScore/$tmpMaxScore")
  }
  val percent = Math.round((score.toFloat / maxScore.toFloat) * 100)
  println(s"$score/$maxScore -> $percent %")

 */
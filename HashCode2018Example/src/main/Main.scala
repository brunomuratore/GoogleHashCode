package main

object Main extends App{

  val allFiles = List[String]("input.txt", "small.in", "medium.in", "big.in")
//  val allFiles = List("input.txt")
//  val allFiles = List("small.in")
//  val allFiles = List("medium.in")
//  val allFiles = List("big.in")

  var totalScore = 0
  var maxTotalScore = 0

  allFiles.foreach { file =>
    val (array, min, max) = InputReader.read(file)

    val slices = BasicSolver.solve(array, min, max)

    OutputWriter.write(slices, file)

    val score = Scorer.compute(slices)
    val maxScore = array.length * array(0).length
    totalScore += score
    maxTotalScore += maxScore
    println(s"$score/$maxScore")
  }
  println(s"$totalScore/$maxTotalScore")
}

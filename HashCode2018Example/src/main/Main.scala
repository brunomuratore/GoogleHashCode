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

    val solver = new Solver(array, min, max)

    val slices = solver.solve()

    OutputWriter.write(slices, file)

    val score = Scorer.compute(slices)
    val maxScore = Scorer.maxScore(array)
    totalScore += score
    maxTotalScore += maxScore
    println(s"$file: $score/$maxScore")
  }
  println(s"total: $totalScore/$maxTotalScore")
}

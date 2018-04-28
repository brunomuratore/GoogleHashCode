package main

object Main extends App{

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
}

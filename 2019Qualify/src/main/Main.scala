package main

import main.framework.{Score, ScorePrinter}
import main.io.{InputReader, OutputWriter}
import main.scorer.Scorer

import scala.collection.mutable._

object Main extends App{

  val allFiles = List[String]("input.txt", "small.in", "medium.in", "big.in")
//  val allFiles = List("input.txt")
//  val allFiles = List("small.in")
//  val allFiles = List("medium.in")
//  val allFiles = List("big.in")

  var scores = HashMap[String, Score]()

  allFiles.foreach { file =>
    println(s"Running $file")

    val (array, min, max) = InputReader.read(file)

    val solver = new Solver(array, min, max)(file)

    val slices = solver.solve()

    OutputWriter.write(slices, file)

    scores += file -> Scorer.compute(slices, array)

    println("")
  }
  ScorePrinter.print(scores)
}

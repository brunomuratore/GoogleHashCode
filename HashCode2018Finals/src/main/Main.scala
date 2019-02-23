package main

import main.framework.{Score, ScorePrinter}
import main.io.{InputReader, OutputWriter}
import main.scorer.Scorer

import scala.collection.mutable

object Main extends App{

  val allFiles = List[String]("a_example.in", "b_short_walk.in", "c_going_green.in", "d_wide_selection.in", "e_precise_fit.in", "f_different_footprints.in")
//  val allFiles = List("a_example.in")
//  val allFiles = List("b_short_walk.in")
//  val allFiles = List("c_going_green.in")
//  val allFiles = List("d_wide_selection.in")
//  val allFiles = List("e_precise_fit.in")
//  val allFiles = List("f_different_footprints.in")

  var scores = mutable.HashMap[String, Score]()

  allFiles.foreach { file =>
    println(s"Running $file")

    val (array, min, max) = InputReader.read(file)

    val solver = new Solver(array, min, max)

    val slices = solver.solve()

    OutputWriter.write(slices, file)

    scores += file -> Scorer.compute(slices, array)

    println("")
  }
  ScorePrinter.print(scores)
}

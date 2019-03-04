package main

import main.framework.{Score, ScorePrinter}
import main.io.{InputReader, OutputWriter}
import main.scorer.Scorer

import scala.collection.mutable._

object Main extends App{

  val allFiles = List[String]("a_example.txt", "b_lovely_landscapes.txt", "c_memorable_moments.txt", "e_shiny_selfies.txt")
//  val allFiles = List("a_example.txt")
//  val allFiles = List("b_lovely_landscapes.txt")
//  val allFiles = List("c_memorable_moments.txt")
//  val allFiles = List("d_pet_pictures.txt")
//  val allFiles = List("e_shiny_selfies.txt")

  var scores = HashMap[String, Score]()

  allFiles.foreach { file =>
    println(s"Running $file")

    val (photos) = InputReader.read(file)

    val solver = new Solver(photos)(file)

    val slideShow = solver.solve()

    OutputWriter.write(slideShow, file)

    scores += file -> Scorer.compute(slideShow)

    println("")
  }
  ScorePrinter.print(scores)
}

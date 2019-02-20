package main

import main.framework.{Score, ScorePrinter}
import main.io.{InputReader, OutputWriter}
import main.scorer.Scorer

import scala.collection.mutable

object Main extends App{

  val allFiles = List[String]("kittens.in", "me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in")
//  val allFiles = List("kittens.in")
//  val allFiles = List("me_at_the_zoo.in")
//  val allFiles = List("trending_today.in")
//  val allFiles = List("videos_worth_spreading.in")

  var scores = mutable.HashMap[String, Score]()

  allFiles.foreach { file =>
    println(s"Running $file")

    val (caches, endpoints) = InputReader.read(file)

    val solver = new Solver(caches, endpoints)

    val cachesResult = solver.solve()

    OutputWriter.write(file)

    scores += file -> Scorer.compute(cachesResult)

    println("")
  }
  ScorePrinter.print(scores)
}

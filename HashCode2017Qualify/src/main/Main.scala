package main

import main.framework.{Score, ScorePrinter}
import main.io.{InputReader, OutputWriter}
import main.scorer.Scorer

import scala.collection.mutable

object Main extends App{

  val allFiles = List[String]("me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in", "kittens.in")
//  val allFiles = List("kittens.in")
//  val allFiles = List("me_at_the_zoo.in")
//  val allFiles = List("trending_today.in")
//  val allFiles = List("videos_worth_spreading.in")

  var scores = mutable.HashMap[String, Score]()

  allFiles.foreach { case (file) =>
    println(s"Running $file")

    val (caches, endpoints) = InputReader.read(file)

    val solver = new SolverBruno(caches, endpoints)(file)

    val cachesResult = solver.solve()

    OutputWriter.write(file, cachesResult)

    scores += file -> Scorer.compute(endpoints, cachesResult)

    println(s"free total cache space = ${caches.map(_.freeSpace).sum}")

    println("")
  }
  ScorePrinter.print(scores)
}

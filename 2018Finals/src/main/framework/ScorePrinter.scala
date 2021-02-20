package main.framework

import java.io._
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import scala.collection.mutable.HashMap
import collection.JavaConverters._
import scala.collection.mutable
import scala.util.Try

object ScorePrinter {

  def print(scores: mutable.Map[String, Score]) = {
    scores += "total" -> Score(scores.values.map(_.result).sum, scores.values.map(_.max).sum)
    val bestScores = Try(loadBestScores()).getOrElse(HashMap[String, Score]())
    scores.toList.sortBy(_._2.max).foreach { case (file, score) =>
      val best = bestScores.getOrElse(file, Score(0, 0))
      val dif = score.result - best.result
      val difStr = if (dif > 0) s"  ▲ $dif" else if (dif < 0) s"  ▼ $dif" else ""
      println(s"$file: ${score.result}/${score.max}$difStr")
      if (dif > 0) bestScores(file) = score
    }

    writeBestScores(bestScores)
  }

  def writeBestScores(bestScores: mutable.HashMap[String, Score]) = {
    val file = new File(s"resources/score/best.txt").getAbsolutePath
    val bw = new BufferedWriter(new FileWriter(file))
    bestScores.foreach{case (file, score) => bw.write(s"$file: ${score.result}/${score.max}\n")}
    bw.close()
  }

  def loadBestScores() = {
    val scores = HashMap[String, Score]()
    val path = new File(s"resources/score/best.txt").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    Files.readAllLines(Paths.get(path), Charset.defaultCharset()).asScala.foreach{ line =>
      val split = line.split(": ")
      val file = split(0)
      val scoreSplit = split(1).split("/")
      val result = scoreSplit(0).toInt
      val max = scoreSplit(1).toInt
      scores += file -> Score(result, max)
    }
    scores
  }
}

case class Score (result: Int, max: Int)

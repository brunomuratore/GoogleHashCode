package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class HamiltonianPathSolver(photos: Array[Photo])(implicit file: String) {

  def run() = {
    val slideShow = SlideShow(ListBuffer.empty)

    val pb = new ProgressBar("Greedy", 0)


    slideShow
  }

}
/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}

import scala.collection.{mutable, _}
import scala.util.Random

class Solver(photos: Set[Photo], tagInPhotos: Map[String, Set[Photo]])(implicit file: String) {
  val r = new Random()

  def solve() = {

    run()
    SlideShow(mutable.ListBuffer.empty[Slide])
  }

  private def run() = {
    val bar = new ProgressBar("Greedy solve", 1)

    bar.update()
  }



}
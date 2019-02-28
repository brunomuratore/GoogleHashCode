/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}

import scala.collection.mutable
import scala.util.Random

class Solver(photos: mutable.Set[Photo], tagInPhotos: mutable.Map[String, mutable.Set[Photo]])(implicit file: String) {
  val r = new Random()

  def solve() = {

    run()
    SlideShow(mutable.LinkedList.empty[Slide])
  }

  private def run() = {
    val bar = new ProgressBar("Greedy solve", 1)

    bar.update()
  }



}
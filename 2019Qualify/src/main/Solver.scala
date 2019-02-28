/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}

import scala.collection.{mutable, _}
import scala.util.Random

class Solver(photos: Set[Photo], tagInPhotos: Map[String, Set[Photo]], sortedPhotos: mutable.TreeMap[Int, Set[Photo]])(implicit file: String) {
  val r = new Random()

  def solve() = {

    run()
    SlideShow(mutable.ListBuffer.empty[Slide])
  }

  private def run() = {
    val bar = new ProgressBar("Greedy solve", 1)

    bar.update()
  }

  def pickSlide(): Slide = {
    val slide = new Slide(mutable.ListBuffer[Photo]())

    // grab the max number of tags in a single photo
    val max = sortedPhotos.keys.last

    // grab the first photo which has this number of tags
    val photo1 = sortedPhotos(max).head

    // if it's horizontal return it
    if (!photo1.vertical) {
        slide.photos += photo1
        return slide
    }

    // It's vertical, try to add the next one
    val photo2 = sortedPhotos(max).head
    if (photo2.vertical) {
      slide.photos += photo2
    }

    return slide
  }



}
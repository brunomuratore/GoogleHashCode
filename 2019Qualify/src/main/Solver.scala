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

//  def pickSlide(): Slide = {
//    val max = sortedPhotos.keys.last
//    val photo = sortedPhotos(max).head

//    val slide = Slide()
//    if (!photo.vertical) {
//        slide.photos.
//        return sli(photo: photo)
//    }
//
//    val photo = sortedPhotos(max).head

//  }



}
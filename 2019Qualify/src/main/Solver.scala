/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}

import scala.collection.{mutable, _}
import scala.util.Random

class Solver(photos: Set[Photo], tagInPhotos: Map[String, Array[Photo]], sortedPhotos: mutable.TreeMap[Int, Array[Photo]])(implicit file: String) {

  val r = new Random()

  def solve() = {

    run()
    SlideShow(mutable.ListBuffer.empty[Slide])
  }

  def pickVerticalPhoto(tags: Int): Option[Photo] = {
    tags.to(0).by(-1).foreach { t =>
      val diff = tags - t
      for (photo <- sortedPhotos(tags))
        if (photo.vertical) return Some(photo)
    }
    return None
  }

  def pickSlide(tags: Int): Option[Slide] = {
    tags.to(0).by(-1).foreach { t =>
      val diff = tags - t
      val photoIds = sortedPhotos(tags).indices
      while(photoIds.nonEmpty) {
        val photo = sortedPhotos(tags)(photoIds(r.nextInt(photoIds.length)))
        if (!photo.vertical) return Some(Slide(List(photo)))
        else if (diff > 0) {
          val p = pickVerticalPhoto(tags(diff))
          if (p.isDefined)
            return Some(Slide(List(photo, p.get)))
          else
            return None
        }
      }

    None
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
    val photo1 = sortedPhotos(max)(0)

    // if it's horizontal return it
    if (!photo1.vertical) {
        slide.photos += photo1
        return slide
    }

    // It's vertical, try to add the next one
    for (i <- 1 until sortedPhotos(max).length) {
      var photo2 = sortedPhotos(max)(i)
      if (photo2.vertical) {
        slide.photos += photo2
        return slide
      }
    }

    // it was not possible to find another vertical photo, just return a slide containing single photo
    return slide
  }



}
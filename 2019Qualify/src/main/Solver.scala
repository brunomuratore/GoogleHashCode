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
      while (photoIds.nonEmpty) {
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
    }
    None
  }

  def run() = {
    val bar = new ProgressBar("Greedy solve", 1)

    bar.update()
  }


  def addSlide(slideShow: SlideShow, slide: Slide): SlideShow = {
    // todo: remove photos from all maps
    SlideShow(slideShow.slides :+ slide)
  }

}
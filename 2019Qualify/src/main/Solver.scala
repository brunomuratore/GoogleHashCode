/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable._
import scala.util.Random

class Solver(photos: Map[Photo, Photo], tagInPhotos: Map[String, Map[Photo, Photo]], sortedPhotos: TreeMap[Int,
  Map[Photo, Photo]])(implicit file: String) {

  val r = new Random()

  def solve() = {

    run()
  }

  def pickVerticalPhoto(tags: Int): Option[Photo] = {
    tags.to(0).by(-1).foreach { t =>
      val diff = tags - t
      for (photo <- sortedPhotos(tags).keys)
        if (photo.vertical) return Some(photo)
    }
    return None
  }

  def pickSlide(tags: Int): Option[Slide] = {
    tags.to(0).by(-1).foreach { t =>
      val diff = tags - t
      val photoIds = sortedPhotos(tags).keys.toVector
      while (photoIds.nonEmpty) {
        val idx = photoIds(r.nextInt(photoIds.size))
        val photo:Photo = photos(idx)
        if (!photo.vertical) return Some(Slide(List(photo)))
        else if (diff > 0) {
          val p = pickVerticalPhoto(diff)
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
    val bar = new ProgressBar("Greedy solve", photos.size)

    val slideShow = SlideShow(ListBuffer.empty[Slide])

    addToSlideShow(slideShow, pickSlide(sortedPhotos.keys.last).get)

    while (photos.nonEmpty) {

      var bestPick: Option[Slide] = None
      var bestValue: Int = 0

      0.to(10).foreach { _ =>
        val pick = pickSlide(Scorer.tagsForSlide(slideShow.slides.last).size)

        val transitionScore: Int = if (pick.isDefined) Scorer.scoreForTransition(slideShow.slides.last, pick.get) else 0

        if (!bestPick.isDefined || (transitionScore > bestValue)) {
          bestPick = pick
          bestValue = transitionScore
        }

        slideShow.slides.last
      }
      bar.update()
    }

    slideShow
  }

  def addToSlideShow(slideShow: SlideShow, slide: Slide): Unit = {
    0.until(slide.photos.size - 1).foreach(i => {
      val photo = slide.photos(i)
      photo.tags.foreach(tag => {
        tagInPhotos(tag) -= photo
      })
      sortedPhotos(photo.tags.size) -= photo
      photos -= photo
    })

    slideShow.slides += slide
  }

}
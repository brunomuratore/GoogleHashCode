/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable._
import scala.util.Random
import util.control.Breaks._

class Solver(photos: Map[Photo, Photo], tagInPhotos: Map[String, Map[Photo, Photo]], sortedPhotos: TreeMap[Int,
  Map[Photo, Photo]])(implicit file: String) {

  val r = new Random()

  def solve() = {

    val slideShow = run()

    print("Photos in slideshow: " + slideShow.slides.size)

    slideShow
  }

  def pickVerticalPhoto(tags: Int): Option[Photo] = {
    tags.to(0).by(-1).foreach { t =>
      val diff = tags - t
      if(sortedPhotos.contains(t)) {
        for (photo <- sortedPhotos(t).keys)
          if (photo.vertical) return Some(photo)
      }
    }
    return None
  }

  def pickSlide(tags: Int): Option[Slide] = {
    tags.to(0).by(-1).foreach { t =>
      val diff = tags - t

      if (sortedPhotos.contains(t)) {
        val photoIds: Set[Photo] = Set(sortedPhotos(t).keys.toList: _*)
        while (photoIds.nonEmpty) {
          val idx = photoIds.toVector(r.nextInt(photoIds.size))
          val photo: Photo = photos(idx)
          if (!photo.vertical) return Some(Slide(List(photo)))
          else if (diff > 0) {
            val p = pickVerticalPhoto(diff)
            if (p.isDefined)
              return Some(Slide(List(photo, p.get)))
            else
              photoIds -= idx
            return None
          }
          else {
            photoIds -= idx
          }
        }
      }
    }
    None
  }

  def run() = {
    val bar = new ProgressBar("Greedy solve", photos.size)

    val slideShow = SlideShow(ListBuffer.empty[Slide])

    addToSlideShow(slideShow, pickSlide(sortedPhotos.keys.last).get)

    breakable {
      while (!photos.isEmpty) {

        var bestPick: Option[Slide] = None
        var bestValue: Int = 0

        0.to(10).foreach { _ =>
          val pick = pickSlide(Scorer.tagsForSlide(slideShow.slides.last).size)

          val transitionScore: Int = if (pick.isDefined) Scorer.scoreForTransition(slideShow.slides.last, pick.get) else 0

          if (!bestPick.isDefined || (transitionScore > bestValue)) {
            bestPick = pick
            bestValue = transitionScore
          }
        }

        if (bestPick.isDefined) {
          addToSlideShow(slideShow, bestPick.get)
        }
        else {
          break
        }

        bar.update()
      }
    }

    slideShow
  }

  def addToSlideShow(slideShow: SlideShow, slide: Slide): Unit = {
    slide.photos.indices.foreach(i => {
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
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
  Map[Photo, Photo]], sortedPhotosVert: TreeMap[Int, Map[Photo, Photo]], sortedPhotosHor: TreeMap[Int, Map[Photo,
  Photo]])(implicit file: String) {

  val r = new Random()

  def solve() = {

    val slideShow = run()

    print("Photos in slideshow: " + slideShow.slides.size)

    slideShow
  }

  def run() = {
    val bar = new ProgressBar("Greedy solve", photos.size)

    val slideShow = SlideShow(ListBuffer.empty[Slide])
    val initialphoto: Photo = sortedPhotosHor(sortedPhotosHor.keys.last).head._1
    addToSlideShow(slideShow, Slide(List(initialphoto)))

    breakable {
      while (!photos.isEmpty) {

        var bestPick: Option[Slide] = None
        var bestValue: Int = 0

        0.to(10).foreach { _ =>
          val pick = pickSlideBruno(Scorer.tagsForSlide(slideShow.slides.last))

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
      if(sortedPhotosHor.contains(photo.tags.size))
        if(sortedPhotosHor(photo.tags.size).contains(photo))
          sortedPhotosHor(photo.tags.size) -= photo
      if(sortedPhotosVert.contains(photo.tags.size))
      if(sortedPhotosVert(photo.tags.size).contains(photo))
        sortedPhotosVert(photo.tags.size) -= photo
      photos -= photo
    })

    slideShow.slides += slide
  }

  def getVert(p1: Photo, tags: Int):Option[Photo] = {
    0.to(tags).foreach { t =>
      if(sortedPhotosVert.contains(t)) {
        sortedPhotosVert(t).keys.foreach{ p=>
          if(p != p1) return Some(p)
        }
      }
    }
    None
  }

  def pickSlideBruno(tags: List[String]): Option[Slide] = {
    tags.size.to(0).by(-1).foreach { t =>
      val up = pickSlideBrunoInt(t, tags)
      if (up.isDefined) return up

      val down = pickSlideBrunoInt(-t, tags)
      if (down.isDefined) return down
    }

    None
  }

  def pickSlideBrunoInt(t: Int, tags: List[String]): Option[Slide] = {
    if(sortedPhotosHor.contains(t) && sortedPhotosHor(t).nonEmpty)
      return Some(Slide(List(sortedPhotosHor(t).head._1)))

    if(sortedPhotosVert.contains(t) && sortedPhotosVert(t).nonEmpty) {
      val p1 = sortedPhotosVert(t).head._1
      val p2 = getVert(p1, t)
      if(p2.isDefined)
        return Some(Slide(List(p1, p2.get)))
      else
        None
    }

    None
  }

}
package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks._

class GreedyPhotoSolver(photos: Array[Photo])(implicit file: String) {

  def run() = {
    val slideShow = SlideShow(ListBuffer.empty)

    //Greedy Photo by Photo
    slideShow.slides += Slide.of(photos(0), photos(1))
    var prev = slideShow.slides.head

    val used = mutable.Set(0, 1)

    val pb = new ProgressBar("Greedy", photos.length - 1)
    1.until(photos.length).foreach { x =>
      var best = (prev, -1, -1)

      val (bestPhoto, _, bestIndex) = getBestPhoto(prev, x, used)
      if (bestIndex > -1) {
        used += bestIndex
        val bestSlide = fillSlideIfVertical(prev, bestPhoto, bestIndex, used)
        slideShow.slides += bestSlide
        prev = bestSlide
      }
      pb.update()
    }

    slideShow
  }


  private def getBestPhoto(prev: Slide, x: Int, used: mutable.Set[Int]): (Photo, Int, Int) = {
    var best = (prev.photos.last, -1, -1)
    photos.indices.par.foreach { y =>
      val s = photos(y)
      if(x != y && !used(y)) {
        val score = Scorer.scoreSet(prev.tags, s.tags) + (s.tags.size / 40)
        if(score > best._2) best = (s, score, y)
      }
    }
    best
  }

  private def fillSlideIfVertical(prev: Slide, cur: Photo, x: Int, used: mutable.Set[Int]): Slide = {
    if(cur.vertical) {
      var best = (prev.photos.last, -1, -1)
      photos.indices.par.foreach { y =>
        val s = photos(y)
        if (s.vertical && x != y && !used(y)) {
          val sumTags = cur.tags ++ s.tags
          val score = Scorer.scoreSet(prev.tags, sumTags) + ((40 - sumTags.size) / 40)
          if (score > best._2) best = (s, score, y)
        }
      }
      used += best._3
      Slide.of(cur, best._1)
    } else {
      Slide.of(cur)
    }
  }

}
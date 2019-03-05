package main

import main.framework.ProgressBar
import main.framework.Utils.time
import main.models.{Photo, Slide, SlideShow}
import main.params.Params.isTestCaseE
import main.scorer.Scorer

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random
import scala.util.control.Breaks._

class Solver(photos: Array[Photo])(implicit file: String) {
  val slideShow = new SlideShow(ListBuffer.empty)
  val r = new Random()
  val verticals = photos.filter(_.vertical)
  val horizontals = photos.filter(!_.vertical)

  // from all photos, create slides
  // each slide has either 1 horizontal photo, or 1 vertical photo
  def createSlides() = {
    val slidesBuff = ArrayBuffer[Slide]()
    val usedPhotos = mutable.Set[Int]()

    horizontals.foreach { p =>
      slidesBuff += Slide.of(p)
    }

    0.until(verticals.length).by(2).foreach { i =>
        slidesBuff += Slide.of(verticals(i), verticals(i+1))
    }

    slidesBuff.toArray
  }

  var slidesA: Array[Slide] = _
  def solve() = {
    slidesA = createSlides() //add on global val
    time { run() }

    slideShow
  }

  def getBestSlide(prev: Slide, x: Int, used: mutable.Set[Int]): (Slide, Int, Int) = {
    var best = (prev, -1, -1)
    val ideal = prev.tags.size / 2
    2.until(slidesA.length).par.foreach { y =>
      val s = slidesA(y)
      if(x != y && !used(y)) {
        val score = Scorer.scoreSet(prev.tags, s.tags)
        if(score > best._2) best = (s, score, y)
        if(score == ideal) return best
      }
    }
    best
  }

  def getBestPhoto(prev: Slide, x: Int, used: mutable.Set[Int]): (Photo, Int, Int) = {
    var best = (prev.photos.last, -1, -1)
    val ideal = prev.tags.size / 2
    photos.indices.par.foreach { y =>
      val s = photos(y)
      if(x != y && !used(y)) {
        val score = Scorer.scoreSet(prev.tags, s.tags)
        if(score > best._2) best = (s, score, y)
        if(score == ideal) return best
      }
    }
    best
  }

  def fillIfNeeded(prev: Slide, cur: Photo, x: Int, used: mutable.Set[Int]): Slide = {
    if(cur.vertical) {
      var best = (prev.photos.last, -1, -1)
      val ideal = prev.tags.size / 2
      breakable {
        photos.indices.par.foreach { y =>
          val s = photos(y)
          if (s.vertical && x != y && !used(y)) {
            val score = Scorer.scoreSet(prev.tags, cur.tags ++ s.tags)
            if (score > best._2) best = (s, score, y)
            if (score == ideal) break
          }
        }
      }
      used += best._3
      Slide.of(cur, best._1)
    } else {
      Slide.of(cur)
    }
  }

  def tree(n: Int) = {
    0.until(n).foreach { i =>
      val points = "." * (n-i-1)
      val stars = "*" * (i*2+1)
      println(List(points, stars, points).mkString(""))
    }
  }

  def run() = {

    if(isTestCaseE) {
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
          val bestSlide = fillIfNeeded(prev, bestPhoto, bestIndex, used)
          slideShow.slides += bestSlide
          prev = bestSlide
        }
        pb.update()
      }
    } else {
      // Greedy slide by slide
      var prev = slidesA(0)
      slideShow.slides += prev

      val used = mutable.Set(0)

      val pb = new ProgressBar("Greedy", slidesA.length - 1)
      1.until(slidesA.length).foreach { x =>
        var best = (prev, -1, -1)

        val (bestSlide, _, bestIndex) = getBestSlide(prev, x, used)
        if (bestIndex > -1) {
          slideShow.slides += bestSlide
          used += bestIndex
          prev = bestSlide
        }
        pb.update()
      }
    }

    slideShow
  }

}
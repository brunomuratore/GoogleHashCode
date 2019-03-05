package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class GreedySlideSolver(photos: Array[Photo])(implicit file: String) {
  private val verticals = photos.filter(_.vertical)
  private val horizontals = photos.filter(!_.vertical)
  private val slides: Array[Slide] = createSlides()

  def run() = {
    val slideShow = SlideShow(ListBuffer.empty)

    // Greedy slide by slide
    var prev = slides(0)
    slideShow.slides += prev

    val used = mutable.Set(0)

    val pb = new ProgressBar("Greedy", slides.length - 1)
    1.until(slides.length).foreach { x =>
      var best = (prev, -1, -1)

      val (bestSlide, _, bestIndex) = getBestSlide(prev, x, used)
      if (bestIndex > -1) {
        slideShow.slides += bestSlide
        used += bestIndex
        prev = bestSlide
      }
      pb.update()
    }

    slideShow
  }

  private def getBestSlide(prev: Slide, x: Int, used: mutable.Set[Int]): (Slide, Int, Int) = {
    var best = (prev, -1, -1)
    val ideal = prev.tags.size / 2
    2.until(slides.length).par.foreach { y =>
      val s = slides(y)
      if(x != y && !used(y)) {
        val score = Scorer.scoreSet(prev.tags, s.tags)
        if(score > best._2) best = (s, score, y)
        if(score == ideal) return best
      }
    }
    best
  }

  // from all photos, create slides
  // each slide has either 1 horizontal photo, or 2 vertical photos
  private def createSlides() = {
    val slidesBuff = ArrayBuffer[Slide]()
    val usedPhotos = mutable.Set[Int]()

    horizontals.foreach { p =>
      slidesBuff += Slide.of(p)
    }

    verticals.indices.by(2).foreach { i =>
      slidesBuff += Slide.of(verticals(i), verticals(i+1))
    }

    slidesBuff.toArray
  }
}
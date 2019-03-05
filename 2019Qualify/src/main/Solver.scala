package main

import main.framework.Utils.time
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

class Solver(photos: Array[Photo])(implicit file: String) {
  val slideShow = new SlideShow(ListBuffer.empty)
  val r = new Random()
  val verticals = photos.filter(_.vertical)
  val horizontals = photos.filter(!_.vertical)

  // to remove random element: Create shuffled array of ids to remove, and keep counter of array index

  // get random vertical photo to form a slide with another vertical photo
  val rndPair = Random.shuffle(verticals.indices.toList)
  var rndPairIdx = -1
  def getVerticalPhotoToFillSlide(p: Photo): Photo = {
    rndPairIdx += 1
    verticals(rndPair(rndPairIdx))
  }

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

  def groupByTags(slides: Array[Slide]) = {
    slides.foreach{s=>
      s.tags.foreach{t=>
        if(!tag2Slide.contains(t)) tag2Slide(t) = mutable.Map.empty
        tag2Slide(t) += s -> true
      }
    }
  }

  var slides: Set[Slide] = _
  var slidesA: Array[Slide] = _
  val tag2Slide: mutable.Map[String, mutable.Map[Slide, Boolean]] = mutable.Map.empty
  def solve() = {
    val slidesArr = createSlides() //add on global val
    //groupByTags(slidesArr) //add on global val
    slidesA = slidesArr
    time { run() }

    slideShow
  }

  def addSlide(slide: Slide) = {
    slideShow.slides += slide
    slide.tags.foreach{t =>
      tag2Slide(t) -= slide
    }
  }

  def getIdealIdx(keys: Array[Int], ideal: Int): Int = {
    var best = (0, 0)
    keys.indices.foreach{ idx =>
      if(keys(idx) == ideal) return idx
      val dif = Math.abs(ideal - keys(idx))
      if(dif < best._2) best = (idx, dif)
    }
    best._1
  }

  def getIdealRange(ideal: Int, keys: Array[Int]): List[Int] = {
    val result = ListBuffer[Int]()
    val sortedKeys = keys.sorted
    val min = sortedKeys.head
    val max = sortedKeys.last
    var idx = getIdealIdx(keys, ideal)
    var idxUp = idx + 1
    while(idx >= 0 || idxUp < keys.length) {
      if(idx >= 0) {
        result += keys(idx)
        idx -= 1
      }
      if(idxUp < keys.length) {
        result += keys(idxUp)
        idxUp += 1
      }
    }
    result.toList
  }

  def getBest(cnt2Slides: Map[Int, List[Slide]], ideal: Int, prev: Slide): Slide = {
    var best = (slides.head, 0)
    getIdealRange(ideal, cnt2Slides.keys.toArray).foreach{idx =>
      //TODO: Improve here, try to get best
      return cnt2Slides(idx).head
    }
    best._1
  }

  def getBest(prev: Slide, x: Int, used: mutable.Set[Int]): (Slide, Int, Int) = {
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

  def run() = {
    var prev = slidesA(0)
    slideShow.slides += prev

    val used = mutable.Set(0)

    1.until(slidesA.length).foreach{x =>
      var best = (prev, -1, -1)

      val (bestSlide, _, bestIndex) = getBest(prev, x, used)
      if(bestIndex > -1) {
        slideShow.slides += bestSlide
        used += bestIndex
        prev = bestSlide
      }
    }

    slideShow
  }

}
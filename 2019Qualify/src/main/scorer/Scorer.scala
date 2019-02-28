package main.scorer

import main.framework.{ProgressBar, Score}
import main.models.{Slide, SlideShow}

import scala.collection.mutable._

object Scorer {

  def tagsForSlide(slide: Slide): Set[String] = {
    val tags:Set[String] = Set[String]()

    slide.photos.foreach{p =>
      tags ++= p.tags
    }

    tags
  }

  def scoreForTransition(slide1: Slide, slide2: Slide): Int = {
    val tagsIn1 = tagsForSlide(slide1)
    val tagsIn2 = tagsForSlide(slide2)

    val tagsInCommon = tagsIn1.intersect(tagsIn2)
    val tags1Only = tagsIn1.size - tagsInCommon.size
    val tags2Only = tagsIn2.size - tagsInCommon.size

    Math.min(tagsInCommon.size, Math.min(tags1Only, tags2Only))
  }

  def compute(slideShow: SlideShow): Score = {
    var pb = new ProgressBar("Scorer", slideShow.slides.size)
    var addedScore: Int = 0

    if (slideShow.slides.size < 1) {
      return Score(0, 0)
    }

    1.until(slideShow.slides.size).foreach { slideIndex: Int =>

      val prevSlide = slideShow.slides(slideIndex-1)
      val curSlide = slideShow.slides(slideIndex)

      addedScore += scoreForTransition(prevSlide, curSlide)
      pb.update()
    }

    val result = addedScore
    val max = 0

    Score(result, max)
  }
}

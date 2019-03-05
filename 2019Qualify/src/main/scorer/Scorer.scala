package main.scorer

import main.framework.{ProgressBar, Score}
import main.models.{Slide, SlideShow}

object Scorer {

  def tagsForSlide(slide: Slide): Set[String] = {
    if(slide.photos.size == 1)
      slide.photos.head.tags
    else
      slide.photos.head.tags ++ slide.photos.last.tags
  }

  def scoreSet(tagsIn1: Set[String], tagsIn2: Set[String]): Int = {
    val tagsInCommon = tagsIn1.intersect(tagsIn2).size
    val tags1Only = Math.abs(tagsIn1.size - tagsInCommon)
    val tags2Only = Math.abs(tagsIn2.size - tagsInCommon)

    Math.min(tagsInCommon, Math.min(tags1Only, tags2Only))
  }

  def scoreForTransition(slide1: Slide, slide2: Slide): Int = {
    val tagsIn1 = tagsForSlide(slide1)
    val tagsIn2 = tagsForSlide(slide2)

    val tagsInCommon = tagsIn1.intersect(tagsIn2)
    val tags1Only = Math.abs(tagsIn1.size - tagsInCommon.size)
    val tags2Only = Math.abs(tagsIn2.size - tagsInCommon.size)

    Math.min(tagsInCommon.size, Math.min(tags1Only, tags2Only))
  }

  def compute(slideShow: SlideShow): Score = {
    var pb = new ProgressBar("Scorer", slideShow.slides.size)
    var addedScore: Int = 0

    if (slideShow.slides.size < 1) {
      return Score(0, 0)
    }

    var prev = slideShow.slides.head
    slideShow.slides.foreach { slide =>
      addedScore += scoreForTransition(prev, slide)
      prev = slide
      pb.update()
    }

    Score(addedScore, 0)
  }
}

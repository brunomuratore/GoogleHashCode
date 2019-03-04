package main.scorer

import main.framework.{ProgressBar, Score}
import main.models.{Slide, SlideShow}

object Scorer {

  def tagsForSlide(slide: Slide): Array[String] = {
    if(slide.photos.size == 1)
      slide.photos.head.tags
    else
      slide.photos.head.tags ++ slide.photos.last.tags
  }

  def score(tagsIn1: Array[String], tagsIn2: Array[String]): Int = {
    val tagsInCommon = tagsIn1.intersect(tagsIn2)
    val tags1Only = Math.abs(tagsIn1.length - tagsInCommon.length)
    val tags2Only = Math.abs(tagsIn2.length - tagsInCommon.length)

    Math.min(tagsInCommon.length, Math.min(tags1Only, tags2Only))
  }

  def scoreSet(tagsIn1: Set[String], tagsIn2: Set[String]): Int = {
    val tagsInCommon = tagsIn1.intersect(tagsIn2).size
    val tags1Only = Math.abs(tagsIn1.size - tagsInCommon)
    val tags2Only = Math.abs(tagsIn2.size - tagsInCommon)

    Math.min(tagsInCommon, Math.min(tags1Only, tags2Only))
  }

  def scoreSet2(tagsIn1: Set[String], tagsIn2: Set[String]): Int = {
    var tagsInCommon = 0
    for(t <- tagsIn1) {
      if(tagsIn2.contains(t)) tagsInCommon += 1
    }
    val tags1Only = Math.abs(tagsIn1.size - tagsInCommon)
    val tags2Only = Math.abs(tagsIn2.size - tagsInCommon)

    Math.min(tagsInCommon, Math.min(tags1Only, tags2Only))
  }

  def scoreForTransition(slide1: Slide, slide2: Slide): Int = {
    val tagsIn1 = tagsForSlide(slide1)
    val tagsIn2 = tagsForSlide(slide2)

    val tagsInCommon = tagsIn1.intersect(tagsIn2)
    val tags1Only = Math.abs(tagsIn1.length - tagsInCommon.length)
    val tags2Only = Math.abs(tagsIn2.length - tagsInCommon.length)

    Math.min(tagsInCommon.length, Math.min(tags1Only, tags2Only))
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

package main.scorer

import main.framework.Score
import main.models.Photo
import main.models.Slide
import main.models.SlideShow
import scala.collection.mutable._

object Scorer {

  def tagsForSlide(slide: Slide): Set[String] = {
    var tags:Set[String] = Set[String]()

    val it = slide.photos.iterator

    while (it.hasNext) {
      val photo = it.next()

      photo.tags.foreach(tags.add(_))
    }

    tags
  }

  def scoreForTransition(slide1: Slide, slide2: Slide): Int = {
    val tagsIn1 = tagsForSlide(slide1)
    val tagsIn2 = tagsForSlide(slide2)

    val tagsInCommon = tagsIn1.filter(tag => tagsIn2.contains(tag))
    val tags1Only = tagsIn1.filter(tag => !tagsIn2.contains(tag))
    val tags2Only = tagsIn2.filter(tag => !tagsIn1.contains(tag))

    Math.min(tagsInCommon.size, Math.min(tags1Only.size, tags2Only.size))
  }

  def compute(slideShow: SlideShow): Score = {

    var addedScore: Int = 0

    if (slideShow.slides.size < 1) {
      return Score(0, 0)
    }

    1.to(slideShow.slides.size-1).foreach { slideIndex: Int =>

      val prevSlide = slideShow.slides(slideIndex-1)
      val curSlide = slideShow.slides(slideIndex)

      addedScore += scoreForTransition(prevSlide, curSlide)
    }

    val result = addedScore
    val max = 0

    Score(result, max)
  }
}

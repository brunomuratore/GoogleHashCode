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

  def compute(slideShow: SlideShow): Score = {

    var addedScore: Int = 0

    if (slideShow.slides.size < 1) {
      return Score(0, 0)
    }

    1.to(slideShow.slides.size).foreach { slideIndex: Int =>

      val prevSlide = slideShow.slides(slideIndex-1)
      val curSlide = slideShow.slides(slideIndex)

      val tagsInPrev = tagsForSlide(prevSlide)
      val tagsInCur = tagsForSlide(curSlide)

      val tagsInCommon = tagsInCur.filter(tag => tagsInPrev.contains(tag))
      val tagsPrevOnly = tagsInPrev.filter(tag => !tagsInCur.contains(tag))
      val tagsCurOnly = tagsInCur.filter(tag => !tagsInPrev.contains(tag))

      addedScore += Math.min(tagsInCommon.size, Math.min(tagsPrevOnly.size, tagsCurOnly.size))
    }

    val result = addedScore
    val max = 0

    Score(result, max)
  }
}

package main.models

import scala.collection.mutable

case class Photo(id: Int, vertical: Boolean, tags: Array[String])

case class Slide(photos: List[Photo], tags: Set[String])

case class SlideShow(slides: mutable.ListBuffer[Slide])

object Slide{
  def of(p1: Photo) = Slide(List(p1), p1.tags.toSet)
  def of(p1: Photo, p2: Photo) = Slide(List(p1, p2), p1.tags.toSet ++ p2.tags.toSet)
}
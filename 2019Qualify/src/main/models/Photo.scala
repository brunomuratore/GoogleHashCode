package main.models

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class Photo(id: Int, vertical: Boolean, tags: Set[String])

case class Slide(photos: ListBuffer[Photo], var tags: Set[String]){
  val isComplete: Boolean = !photos.head.vertical || photos.size == 2
}

case class SlideShow(slides: mutable.ListBuffer[Slide])

object Slide{
  def of(p1: Photo) = Slide(ListBuffer(p1), p1.tags.toSet)
  def of(p1: Photo, p2: Photo) = Slide(ListBuffer(p1, p2), p1.tags.toSet ++ p2.tags.toSet)
}
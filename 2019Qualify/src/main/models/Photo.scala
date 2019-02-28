package main.models

import scala.collection.mutable

case class Photo(id: Int, vertical: Boolean, tags: Set[String])

case class Slide(photos: mutable.ListBuffer[Photo])

case class SlideShow(slides: mutable.ListBuffer[Slide])

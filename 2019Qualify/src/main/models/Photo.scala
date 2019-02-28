package main.models

import scala.collection.mutable

case class Photo(id: Int, vertical: Boolean, tags: Set[String])

case class Slide(photos: mutable.LinkedList[Photo])

case class SlideShow(slides: mutable.LinkedList[Slide])

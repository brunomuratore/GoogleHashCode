package main

import scala.collection.mutable.ListBuffer

object Scorer {
  def compute(slices: ListBuffer[Slice]): Int = slices.map(_.size).sum
}

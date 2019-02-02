package main

import scala.collection.mutable.ListBuffer

object Scorer {
  def maxScore(array: Array[Array[Int]]) = array.length * array(0).length

  def compute(slices: ListBuffer[Slice]): Int = slices.map(_.size).sum
}

package main.scorer

import main.framework.Score
import main.models.Slice

import scala.collection.mutable.ListBuffer

object Scorer {
  def compute(slices: Iterable[Slice], array: Array[Array[Int]]): Score = {
    val result = slices.map(_.size).sum
    val max = array.length * array(0).length

    Score(result, max)
  }
}

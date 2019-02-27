package main.scorer

import main.framework.Score
import main.models.Slice

object Scorer {
  def compute(slices: Iterable[Slice], array: Array[Array[Int]]): Score = {
    // CHANGE HERE: calculate current score, and maximum score
    val result = 0
    val max = 0

    Score(result, max)
  }
}

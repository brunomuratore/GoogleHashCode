package main

import scala.collection.mutable.ArrayBuffer

object Scorer {

  def maxScorer(bonus: Int, rides: ArrayBuffer[Ride]) = {
    var score = 0
    rides.foreach(r => score += r.distance + bonus)
    score
  }

  def compute(bonus: Int, rides: ArrayBuffer[Ride], vehicles: Array[Vehicle], steps: Int): Int = {
    var score = 0
    vehicles.foreach(v => {
      v.rides.foreach(r => {
        if ((r.started + r.distance ) <= r.timeEnd) {
          score += r.distance
          if (r.timeStart == r.started) {
            score += bonus
          }
        }
      })
    })
    score
  }
}

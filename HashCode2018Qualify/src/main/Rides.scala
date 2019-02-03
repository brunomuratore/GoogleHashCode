package main

import java.util
import java.util.function

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Rides extends util.TreeMap[Int, mutable.ListBuffer[Ride]] {

  def add(ride: Ride): Unit = {
    var rides = computeIfAbsent(ride.timeStart, new function.Function[Int, mutable.ListBuffer[Ride]] {
      override def apply(t: Int): ListBuffer[Ride] = new mutable.ListBuffer[Ride]
    })
    rides += ride
  }

  def find1(vehicle: Vehicle): Option[Ride] = {
    val accessible = tailMap(vehicle.available)

    val iterator = accessible.entrySet().iterator()
    while (iterator.hasNext) {
      val entry = iterator.next()
      val rides = entry.getValue
      if (rides.isEmpty) {
        iterator.remove()
      } else {
        // Add here logic to select a better ride.
        val ride = rides.head
        rides -= ride
        return Some(ride)
      }
    }

    None
  }

  def canMake(ride: Ride, v: Vehicle, maxTime: Int): Boolean = {
    val finishTime = BasicSolver.getAvailable(v, ride)
    finishTime <= maxTime && finishTime <= ride.timeEnd
  }

  def find4(vehicle: Vehicle, maxTime: Int): Option[Ride] = {
    val accessible = tailMap(0)
    val iterator = accessible.entrySet().iterator()
    var best: Ride = null
    var diff = 0
    var buffer: mutable.ListBuffer[Ride] = null
    while (iterator.hasNext) {
      val entry = iterator.next()
      val rides = entry.getValue
      if (rides.isEmpty) {
        iterator.remove()
      } else {
        // Add here logic to select a better ride.
        for (ride <- rides) {
          val distance = D.dist(vehicle.position, ride.start)
          if (best ne null) {
            val d = Math.abs(vehicle.available + distance - ride.timeStart)
            if (d < diff) {
              if (canMake(ride, vehicle, maxTime)) {
                best = ride
                diff = d
                buffer = rides
              }
            }
          } else {
            if (canMake(ride, vehicle, maxTime)) {
              best = ride
              diff = Math.abs(vehicle.available + distance - ride.timeStart)
              buffer = rides
            }
          }
        }
      }
    }
    if (best ne null) {
      buffer -= best
      Some(best)
    } else None
  }

  def find5(vehicle: Vehicle, maxTime: Int, bonus: Int): Option[Ride] = {
    val accessible = tailMap(0)
    val iterator = accessible.entrySet().iterator()
    var best: Ride = null
    var diff:Double = 0
    var buffer: mutable.ListBuffer[Ride] = null
    while (iterator.hasNext) {
      val entry = iterator.next()
      val rides = entry.getValue
      if (rides.isEmpty) {
        iterator.remove()
      } else {
        // Add here logic to select a better ride.
        for (ride <- rides) {
          val distance = D.dist(vehicle.position, ride.start)
          if (canMake(ride, vehicle, maxTime)) {
            if (best ne null) {
              val d = getScore(vehicle, ride, distance, bonus)
              if (d > diff) {
                best = ride
                diff = d
                buffer = rides
              }
            } else {
              best = ride
              diff = getScore(vehicle, ride, distance, bonus)
              buffer = rides
            }
          }
        }
      }
    }
    if (best ne null) {
      buffer -= best
      Some(best)
    } else None
  }

  private def getScore(vehicle: Vehicle, ride: Ride, distance: Int, bonus: Int): Double = {
    var score:Double = 0
    val startTime = BasicSolver.getStartTime(vehicle, ride)
    if (startTime == ride.timeStart) {
      score += bonus
    }
    val waitTime = startTime - vehicle.available
    score - waitTime
  }
}

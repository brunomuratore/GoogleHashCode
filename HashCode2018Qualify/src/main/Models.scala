package main

import scala.collection.mutable.ArrayBuffer


case class Ride(id: Int, start: Point, dest: Point, timeStart: Int, timeEnd: Int) {
  val distance: Int = Math.abs(start.x - dest.x) + Math.abs(start.y - dest.y)
  var started: Int = -1
}

case class Vehicle(id: Int, rides: ArrayBuffer[Ride] = ArrayBuffer()) {
  var available: Int = 0
  var position: Point = Point(0, 0)
}

case class Point(x: Int, y: Int)

object VehicleOrdering extends Ordering[Vehicle] {
  override def compare(x: Vehicle, y: Vehicle): Int = y.available compare x.available
}

object D {
  def dist(p1: Point, p2: Point): Int = {
    Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y)
  }
}
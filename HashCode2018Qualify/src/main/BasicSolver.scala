/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import scala.collection.mutable.ArrayBuffer

object BasicSolver {

  // Assume Car can finish the ride
  def getStartTime(v: Vehicle, ride: Ride): Int = {
    val d = D.dist(v.position, ride.start)
    var startTime = v.available + d
    if (startTime < ride.timeStart)
      startTime = ride.timeStart
    startTime
  }

  def getAvailable(v: Vehicle, ride: Ride): Int = {
    val endTime = getStartTime(v, ride) + ride.distance
    endTime
  }

  def solve(rows: Int, columns: Int, fleet: Int, ridesA: ArrayBuffer[Ride], steps: Int, bonus: Int): Array[Vehicle] = {
    val vehicleArr = (1 to fleet).map(Vehicle(_)).toArray
    val vehicles = scala.collection.mutable.PriorityQueue.empty[Vehicle](VehicleOrdering)
    vehicles.enqueue(vehicleArr:_*)

    val rides = new Rides()
    ridesA.foreach(rides.add)

     while(vehicles.nonEmpty) {
       val v = vehicles.dequeue()

       val ride = rides.find5(v, steps, bonus)

       if(ride.isDefined) {
         v.rides += ride.get
         ride.get.started = getStartTime(v, ride.get)
         v.available = getAvailable(v, ride.get)
         v.position = ride.get.dest

         vehicles.enqueue(v)
       }
     }

    vehicleArr
  }

}

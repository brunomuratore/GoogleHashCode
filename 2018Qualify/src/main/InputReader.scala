package main

import java.io.{BufferedReader, File, FileReader}

import scala.collection.mutable.ArrayBuffer

object InputReader {
  def read(file: String): (Int, Int, Int, Int, ArrayBuffer[Ride], Int) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    val line = reader.readLine().split(" ")
    val rows = line(0).toInt
    val cols = line(1).toInt
    val fleet = line(2).toInt
    val numRides = line(3).toInt
    val bonus = line(4).toInt
    val steps = line(5).toInt

    val rides = new ArrayBuffer[Ride](numRides)

    0.until(numRides).foreach(r => {
      val ride = reader.readLine().split(" ")
      rides += Ride(r, Point(ride(0).toInt, ride(1).toInt), Point(ride(2).toInt, ride(3).toInt),
        ride(4).toInt, ride(5).toInt)
    })

    (rows, cols, fleet, bonus, rides, steps)
  }
}

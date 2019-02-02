/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Solver(pizza: Array[Array[Int]], minOfEach: Int, maxSize: Int) {

  var result = ListBuffer[Slice]()

  def solve(): ListBuffer[Slice] = {
    result = ListBuffer[Slice]()

    run()

    result //global, calculated by run()
  }

  def getSlicesByMax(point: Point) = {
    getEndPoints(point, point).values.map(p => Slice(point, p)).toList.sortBy(-_.size)
  }
  private val minSize = minOfEach * 2
  def getEndPoints(p1: Point, p2: Point, setPoints: mutable.HashMap[Point, Point] = mutable.HashMap()): mutable.HashMap[Point, Point] = {
    val slice = Slice(p1, p2)
    if(setPoints.contains(p2)) return null
    if(slice.size > maxSize) return null
    if (p2.row < 0 || p2.row >= pizza.length) return null
    if (p2.col < 0 || p2.col >= pizza(0).length) return null
    if(pizza(p2.row)(p2.col) == 3) return null
    setPoints += p2 -> p2
    getEndPoints(p1, Point(p2.row, p2.col + 1), setPoints)
    getEndPoints(p1, Point(p2.row+1, p2.col), setPoints)
    getEndPoints(p1, Point(p2.row, p2.col - 1), setPoints)
    getEndPoints(p1, Point(p2.row-1, p2.col), setPoints)
    setPoints
  }

  def findSlice(point: Point): Option[Slice] = {
    val slices = getSlicesByMax(point)
    for (slice <- slices) {
      if(slice.isValid(minOfEach, maxSize, pizza))
        return Some(slice)
    }
    None
  }

  private def run() = {
    pizza.indices.foreach { x =>
      pizza(0).indices.foreach { y =>
        if (pizza(x)(y) != 3)
          findSlice(Point(x, y)).foreach(cutPizza)
      }
    }
  }

  private def cutPizza(slice: Slice): Unit = {
    result += slice
    val startRow = Math.max(0, Math.min(slice.p1.row, slice.p2.row))
    val endRow = Math.min(pizza.length - 1, Math.max(slice.p1.row, slice.p2.row))
    val startCol = Math.max(0, Math.min(slice.p1.col, slice.p2.col))
    val endCol = Math.min(pizza(0).length - 1, Math.max(slice.p1.col, slice.p2.col))
    startRow.to(endRow).foreach { x =>
      startCol.to(endCol).foreach { y =>
        pizza(x)(y) = 3
      }
    }
  }

}
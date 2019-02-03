/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.models.{Point, Slice}
import main.models.Slice.getSliceId

import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

class Solver(pizza: Array[Array[Int]], minOfEach: Int, maxSize: Int) {
  var result = HashMap[Int, Slice]()

  def solve(): HashMap[Int, Slice] = {
    result = HashMap[Int, Slice]()

    run()

    result //global, calculated by run()
  }

  def getSlicesByMax(point: Point): List[Slice] = {
    getEndPoints(point, point).values.map(p => Slice(getSliceId, point, p)).toList.sortBy(-_.size)
  }
  private val minSize = minOfEach * 2
  def getEndPoints(p1: Point, p2: Point, setPoints: HashMap[Point, Point] = HashMap()): HashMap[Point, Point] = {
    val slice = Slice(0, p1, p2)
    if(setPoints.contains(p2)) return null
    if(slice.size > maxSize) return null
    if (p2.row < 0 || p2.row >= pizza.length) return null
    if (p2.col < 0 || p2.col >= pizza(0).length) return null
    if(pizza(p2.row)(p2.col) >= 3) return null
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
        if (pizza(x)(y) < 3)
          findSlice(Point(x, y)).foreach(cutPizza)
      }
    }

    improveEdges()
  }

  def improveEdges() = {

  }

  private def cutPizza(slice: Slice): Unit = {
    result += slice.id -> slice
    val startRow = Math.max(0, Math.min(slice.p1.row, slice.p2.row))
    val endRow = Math.min(pizza.length - 1, Math.max(slice.p1.row, slice.p2.row))
    val startCol = Math.max(0, Math.min(slice.p1.col, slice.p2.col))
    val endCol = Math.min(pizza(0).length - 1, Math.max(slice.p1.col, slice.p2.col))
    startRow.to(endRow).foreach { x =>
      startCol.to(endCol).foreach { y =>
        pizza(x)(y) = slice.id
      }
    }
  }

}
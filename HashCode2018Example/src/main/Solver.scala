/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.models.{Point, Slice}
import main.models.Slice.getSliceId

import scala.collection.mutable
import scala.collection.mutable.Set
import scala.collection.mutable.Queue
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.util.Random

class Solver(pizza: Array[Array[Int]], minOfEach: Int, maxSize: Int) {
  private val originalPizza = pizza.map(_.clone)
  var result = HashMap[Int, Slice]()
  val r = new Random()

  def solve(): HashMap[Int, Slice] = {
    result = HashMap[Int, Slice]()

    run()

    result //global, calculated by run()
  }

  def getSlicesByMax(point: Point): List[Slice] = {
    getEndPoints(point, point).values.map(p => Slice(getSliceId, point, p)).toList.sortBy(-_.size)
  }
  private val minSize = minOfEach * 2

  def isOnBound(p: Point) = !(p.row < 0 || p.row >= pizza.length || p.col < 0 || p.col >= pizza(0).length)

  def getEndPoints(p1: Point, p2: Point, setPoints: HashMap[Point, Point] = HashMap()): HashMap[Point, Point] = {
    val slice = Slice(0, p1, p2)
    if(setPoints.contains(p2)) return HashMap.empty
    if(slice.size > maxSize) return HashMap.empty
    if(!isOnBound(p2)) return HashMap.empty
    if(pizza(p2.row)(p2.col) >= 3) return HashMap.empty
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

  def getRandomEdgePoint() = r.nextInt(4) match{
    case 0 => Point(0, r.nextInt(pizza(0).length))
    case 1 => Point(pizza.length - 1, r.nextInt(pizza(0).length))
    case 2 => Point(r.nextInt(pizza.length), 0)
    case 3 => Point(r.nextInt(pizza.length), pizza(0).length - 1)
  }

  def getFreeEdge(): Option[Point] = {
    0.to(1000).foreach{_ =>
      val edgePoint = getRandomEdgePoint()
      if (pizza(edgePoint.row)(edgePoint.col) < 3)
        return Some(edgePoint)
    }
    None
  }

  def calculateFreeSpaceAndFindNearestSlice(edgePoint: Point): (Int, Set[Int]) = {
    var nearestSliceIds = Set[Int]()
    var freeSpace = 0
    val queue = Queue[Point]()
    val visited = Set[Point]()
    queue += edgePoint
    while(queue.nonEmpty) {
      val p = queue.dequeue()
      if (!visited.contains(p) && isOnBound(p)) {
        visited += p
        if (pizza(p.row)(p.col) >= 3) {
          if(nearestSliceIds.size < 1)
            nearestSliceIds += pizza(p.row)(p.col)
        } else {
          freeSpace += 1
          queue += Point(p.row+1,p.col)
          queue += Point(p.row,p.col+1)
          queue += Point(p.row-1,p.col)
          queue += Point(p.row,p.col-1)
        }
      }
    }
    (freeSpace, nearestSliceIds)
  }

  def bfsSolveFromFreePoint(start: Point): mutable.MutableList[Int] = {
    val queue = Queue[Point]()
    val visited = Set[Point]()
    queue += start
    while(queue.nonEmpty) {
      val p = queue.dequeue()
      if (!visited.contains(p) && isOnBound(p)) {
        visited += p
        if (pizza(p.row)(p.col) < 3) {
          queue += Point(p.row+1,p.col)
          queue += Point(p.row,p.col+1)
          queue += Point(p.row-1,p.col)
          queue += Point(p.row,p.col-1)
        }
      }
    }
    val InsertedSlicesIds = mutable.MutableList[Int]()
    visited.foreach(p =>findSlice(p).foreach{slice =>
      cutPizza(slice)
      InsertedSlicesIds += slice.id
    })
    InsertedSlicesIds
  }

  def removeSlice(nearSliceId: Int): Slice = {
    val nearSlice = result(nearSliceId)
    result -= nearSliceId
    nearSlice.allPoints.foreach(p => pizza(p.row)(p.col) = originalPizza(p.row)(p.col))
    nearSlice
  }

  def improveEdges(): Unit = {
    0.to(1000).foreach { _ =>
      val edgePoint = getFreeEdge()
      if(edgePoint.isDefined) {
        val (prevFreeSpace, nearSliceIds) = calculateFreeSpaceAndFindNearestSlice(edgePoint.get)
        val oldSlices = ListBuffer[Slice]()
        nearSliceIds.foreach{slice =>
          oldSlices += result(slice)
          removeSlice(slice)
        }
        val insertedIds = bfsSolveFromFreePoint(edgePoint.get)
        val (curFreeSpace, _) = calculateFreeSpaceAndFindNearestSlice(edgePoint.get)
//        println(s"cur: $curFreeSpace prev: $prevFreeSpace")
        if (curFreeSpace > prevFreeSpace) {
          insertedIds.foreach(removeSlice)
          oldSlices.foreach(cutPizza)
        }
      }
    }
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
/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import scala.collection.mutable.ListBuffer

object BasicSolver {

  var MAX = 0
  var MIN = 0
  var result = ListBuffer[Slice]()

  def getSlices(slice: Slice, pizza: Array[Array[Int]]): ListBuffer[Slice] = {
    val res = ListBuffer[Slice]()
    if (slice.size > MAX) return res
    if (pizza(slice.p2.x)(slice.p2.y) == 3) return res

    if (slice.isValid(MIN, pizza) && slice.size <= MAX)
      res += slice

    if (slice.p2.x < pizza.length - 1) {
      val right = Point(slice.p2.x + 1, slice.p2.y)
      res ++= getSlices(Slice(slice.p1, right), pizza)
    }

    if (slice.p2.y < pizza(0).length - 1) {
      val down = Point(slice.p2.x, slice.p2.y + 1)
      res ++= getSlices(Slice(slice.p1, down), pizza)
    }
    res
  }

  def findMaxSlice(slice: Slice, pizza: Array[Array[Int]]): Slice = {
    val slices = getSlices(slice, pizza)
    maxOptionBy(slices)(_.size).getOrElse(null)
  }

  def maxOptionBy[A, B: Ordering](seq: Seq[A])(f: A => B) =
    seq reduceOption Ordering.by(f).max

  def solve(pizza: Array[Array[Int]], min: Int, max: Int): ListBuffer[Slice] = {
    result = ListBuffer[Slice]()
    MAX = max
    MIN = min

    solve(pizza)
    result //global, calculated by solve()
  }
    
  //score 931943
  private def solve(pizza: Array[Array[Int]]) = {
    val t = (pizza.length + 1) * (pizza(0).length + 1)
    pizza.indices.foreach { x =>
      pizza(x).indices.foreach { y =>
        if (pizza(x)(y) != 3) {
          val slice = findMaxSlice(Slice(Point(x, y), Point(x, y)), pizza)
          cutPizza(pizza, slice)
        }
      }
    }
  }

  def cutPizza(pizza: Array[Array[Int]], slice: Slice) = {
    if (slice != null) {
      slice.p1.x.to (slice.p2.x).foreach { px =>
        slice.p1.y.to (slice.p2.y).foreach {  py =>
          pizza (px) (py) = 3
        }
      }
      result += slice
    }
  }

}

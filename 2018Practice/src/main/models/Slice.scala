package main.models

import scala.collection.mutable

case class Slice(id: Int, p1: Point, p2: Point) {
  def direction = Math.abs(p1.row - p2.row) > Math.abs(p1.col - p2.col)

  def allPoints = {
    val startRow = Math.min(p1.row, p2.row)
    val endRow =  Math.max(p1.row, p2.row)
    val startCol = Math.min(p1.col, p2.col)
    val endCol = Math.max(p1.col, p2.col)
    val points = mutable.MutableList[Point]()
    startRow.to(endRow).foreach { x =>
      startCol.to(endCol).foreach { y =>
        points += Point(x,y)
      }
    }
    points
  }

  val size: Int = (Math.max(p2.row, p1.row) - Math.min(p1.row, p2.row) + 1) *
    ((Math.max(p2.col, p1.col) - Math.min(p1.col, p2.col)) + 1)

  // Returns if a Slice is valid
  // Check if it is within bounds of pizza
  // Check if it has the minimum amount of each ingredient (Tomato = 0, Mushroom = 1)
  // Check if its size is no bigger than maximum size allowed for a Slice
  def isValid(minOfEach: Int, maxSize: Int, pizza: Array[Array[Int]]): Boolean = {
    if (this.size > maxSize) return false
    if (p1.row < 0 || p2.row < 0 || p1.row >= pizza.length || p2.row >= pizza.length) return false
    if (p1.col < 0 || p2.col < 0 || p1.col >= pizza(0).length || p2.col >= pizza(0).length) return false

    var count0 = 0
    var count1 = 0
    val startRow = Math.max(0, Math.min(p1.row, p2.row))
    val endRow = Math.min(pizza.length - 1, Math.max(p1.row, p2.row))
    val startCol = Math.max(0, Math.min(p1.col, p2.col))
    val endCol = Math.min(pizza(0).length - 1, Math.max(p1.col, p2.col))
    startRow.to(endRow).foreach { x =>
      startCol.to(endCol).foreach { y =>
        if (pizza(x)(y) == 0)
          count0 += 1
        else if (pizza(x)(y) == 1)
          count1 += 1
        else if (pizza(x)(y) >= 3) return false
      }
    }
    count0 >= minOfEach && count1 >= minOfEach
  }

}

case class Point(row: Int, col: Int)

object Slice {
  private var sliceId = 3
  def getSliceId: Int = {
    sliceId +=1
    sliceId
  }
}
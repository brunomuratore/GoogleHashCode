package main.models

case class Slice(id: Int, p1: Point, p2: Point)

case class Point(row: Int, col: Int)

object Slice {
  private var sliceId = 3
  def getSliceId: Int = {
    sliceId +=1
    sliceId
  }
}
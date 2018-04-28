package main

import scala.collection.mutable.ArrayBuffer

case class Video(id: Int, size: Int)

case class Endpoint(id: Int, latency: Int, links: ArrayBuffer[Link], requests: ArrayBuffer[Requests])

case class Requests(qty: Int, video: Video)

case class Link(cache: Cache, latency: Int)

case class Cache(id: Int, size: Int, videos: ArrayBuffer[Video])

case class Slice(p1: Point, p2: Point) {
  val size: Int = (p2.x - p1.x + 1) * (p2.y - p1.y + 1)

  def isValid(min: Int, pizza: Array[Array[Int]]): Boolean = {
    var has0 = 0
    var has1 = 0
    p1.x.to(p2.x).foreach { x => {
      p1.y.to(p2.y).foreach { y =>
        if (pizza(x)(y) == 0)
          has0 += 1
        else if (pizza(x)(y) == 1)
          has1 += 1
        else if (pizza(x)(y) == 3) return false
      }
      }
    }
    has0 >= min && has1 >= min
  }

}

case class Point(x: Int, y: Int)

/*
 * Copyright (c) 2019 Schibsted Media Group. All rights reserved.
 */

package main.models

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.MutableList

case class Video(id: Int, size: Int)

case class Endpoint(id: Int, latency: Int, links: ArrayBuffer[Link], requests: ArrayBuffer[Requests])

case class Requests(qty: Int, video: Video) {
  lazy val totalSize = qty * video.size
}

case class Link(cache: Cache, latency: Int)

case class Cache(id: Int, size: Int, var videos: MutableList[Video]) {
  var currentSize = 0
  def addIfHasSpace(video: Video): Boolean = {
    if (currentSize + video.size <=size) {
      videos += video
      currentSize += video.size
      true
    } else {
      false
    }
  }
}
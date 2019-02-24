/*
 * Copyright (c) 2019 Parado No Bailao. All rights reserved.
 */

package main.models

import scala.collection.mutable._

// max 10k
case class Video(id: Int, size: Int)

case class CachedVideo(video: Video, savedLatency: Int)

// max 1k
// caches: cache id / latency
case class Endpoint(id: Int, latency: Int, caches: HashMap[Int, Int], requests: ArrayBuffer[Requests])

// max 1M
case class Requests(qty: Int, video: Video) {
  lazy val totalSize = qty * video.size
}

// max 1k
case class Link(id: Int, latency: Int)

// max 1k, size: max 500k mb
// endpoints: endpoint id / latency
case class Cache(id: Int, size: Int, var cachedVideos: HashMap[Int, CachedVideo], endpoints: HashMap[Int, Int]) {
  var currentSize = 0
  def addIfHasSpace(video: Video, savedLatency: Int): Boolean = {
    if (currentSize + video.size <=size) {
      cachedVideos += video.id -> CachedVideo(video, savedLatency)
      currentSize += video.size
      true
    } else {
      false
    }
  }
  def freeSpace = size - currentSize

  def getLatencyForEndpointId(endpointId: Int): Int = {
    endpoints(endpointId)
  }
}
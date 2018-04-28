package main

import scala.collection.mutable.ArrayBuffer

case class Video(id: Int, size: Int)

case class Endpoint(id: Int, latency: Int, links: ArrayBuffer[Link], requests: ArrayBuffer[Requests])

case class Requests(qty: Int, video: Video)

case class Link(cache: Cache, latency: Int)

case class Cache(id: Int, size: Int, videos: ArrayBuffer[Video])

package main.io

import java.io.{BufferedReader, File, FileReader}

import main.models.{Cache, Endpoint, Link, Requests, Video}

import scala.collection.mutable._

object InputReader {
  def read(file: String): (ArrayBuffer[Cache], ArrayBuffer[Endpoint]) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    //first line
    var line = reader.readLine().split(" ")
    val aVideos = line(0).toInt
    val aEndpoints = line(1).toInt
    val aRequests = line(2).toInt
    val aCaches = line(3).toInt
    val cacheSize = line(4).toInt

    //caches
    val caches = new ArrayBuffer[Cache](aCaches)
    0.until(aCaches).foreach(caches += Cache(_, cacheSize, HashMap.empty))

    //videos
    val videos = new ArrayBuffer[Video](aVideos)
    line = reader.readLine().split(" ")
    0.until(aVideos).foreach(i =>videos += Video(i, line(i).toInt))

    //endpoints
    val endpoints = new ArrayBuffer[Endpoint](aEndpoints)
    0.until(aEndpoints).foreach { e =>
      line = reader.readLine().split(" ")
      val latency = line(0).toInt
      val linksA = line(1).toInt
      val links = new ArrayBuffer[Link](linksA)
      0.until(linksA).foreach { _ =>
        line = reader.readLine().split(" ")
        val cacheObj = caches(line(0).toInt)
        val cacheLatency = line(1).toInt
        links += Link(cacheObj, cacheLatency)
      }
      endpoints += Endpoint(e, latency, links, new ArrayBuffer[Requests])
    }

    //requests
    val requests = new ArrayBuffer[Requests](aRequests)
    0.until(aRequests).foreach { _ =>
      line = reader.readLine().split(" ")
      val videoId = line(0).toInt
      val endpointId = line(1).toInt
      val totalRequests = line(2).toInt
      endpoints(endpointId).requests += Requests(totalRequests, videos(videoId))
    }

    (caches, endpoints)
  }
}

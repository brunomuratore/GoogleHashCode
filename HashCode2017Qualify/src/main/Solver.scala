/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Cache, Endpoint, Video}

import scala.collection.mutable._
import util.control.Breaks._

class Solver(caches: ArrayBuffer[Cache], endpoints: ArrayBuffer[Endpoint], in: Int) {

  def solve(): ArrayBuffer[Cache] = {
    // distributeVideosToCaches()
    distributeVideosToMultipleCaches()
    // distributeVideosToMultipleCachesWithoutRepeatingVideos()
    caches
  }

  def distributeVideosToCaches() = {
    endpoints.foreach { endpoint =>

      val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1))
      if (bestCaches.nonEmpty) {
        endpoint.requests.foreach { req =>
          val savedLatency = (endpoint.latency - bestCaches.head.getLatencyForEndpointId(endpoint.id)) * req.totalSize
            bestCaches.head.addToInfinityCache(req.video, savedLatency)
        }
      }
    }

    caches.foreach(_.trimCache())
  }

  def distributeVideosToMultipleCachesWithoutRepeatingVideos() = {
    var cachedVideos = scala.collection.mutable.Set[Int]()

    endpoints.foreach { endpoint =>
      val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1)).toArray
      var iCache = 0
      if (bestCaches.nonEmpty) {
        endpoint.requests.foreach { req =>
          if (cachedVideos.contains(req.video.id) == false) {
            if (iCache < bestCaches.length) {
              val savedLatency = (endpoint.latency - bestCaches(iCache).getLatencyForEndpointId(endpoint.id)) * req.totalSize
              var added = bestCaches(iCache).addIfHasSpace(req.video, savedLatency)
              if (added == false) {
                iCache = iCache + 1
                if (iCache < bestCaches.length) {
                  bestCaches(iCache).addIfHasSpace(req.video, savedLatency)
                }
              }
              if (added == true) {
                cachedVideos += req.video.id
              }
            }
          }
        }
      }
    }

    caches.foreach(_.trimCache())
  }

  def distributeVideosToMultipleCaches() = {
    endpoints.foreach { endpoint =>
      val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1)).toArray
      var iCache = 0
      if (bestCaches.nonEmpty) {
        endpoint.requests.foreach { req =>
          if (iCache < bestCaches.length) {
            val savedLatency = (endpoint.latency - bestCaches(iCache).getLatencyForEndpointId(endpoint.id)) * req.totalSize
            val added = bestCaches(iCache).addIfHasSpace(req.video, savedLatency)
            if (added == false) {
              iCache = iCache + 1
              if (iCache < bestCaches.length) {
                bestCaches(iCache).addIfHasSpace(req.video, savedLatency)
              }
            }
          }
        }
      }
    }

    caches.foreach(_.trimCache())
  }


}
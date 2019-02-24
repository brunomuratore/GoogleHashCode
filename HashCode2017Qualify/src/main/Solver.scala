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
    distributeVideosToCaches
    caches
  }

  def distributeVideosToCaches() = {
    var pb = new ProgressBar("Solver", endpoints.size)
    endpoints.foreach { endpoint =>

      val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1))
      if (bestCaches.nonEmpty) {
        endpoint.requests.foreach { req =>
          val savedLatency = (endpoint.latency - bestCaches.head.getLatencyForEndpointId(endpoint.id)) * req.totalSize
            bestCaches.head.addToInfinityCache(req.video, savedLatency)
        }
      }
      pb.update()
    }

    pb = new ProgressBar("Trim", endpoints.size)
    caches.foreach { c =>
      c.trimCache()
      pb.update()
    }
  }

}


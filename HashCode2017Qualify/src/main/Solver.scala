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

    distributeVideosToCaches()

    ArrayBuffer[Cache]()
  }

  def distributeVideosToCaches() = {
    endpoints.foreach { endpoint =>

      var bestCache: Option[Cache] = None

      endpoint.caches.keys.foreach { cacheId =>
        val cache = caches(cacheId)

        if (bestCache.isEmpty) {
          bestCache = Some(cache)
        }

        if (cache.getLatencyForEndpointId(endpoint.id) < bestCache.get.getLatencyForEndpointId(endpoint.id)) {
          bestCache = Some(cache)
        }
      }

      if (bestCache.isDefined) {
        endpoint.requests.foreach { req =>
          bestCache.get.addToInfinityCache(req.video, bestCache.get.getLatencyForEndpointId(endpoint.id))
        }
      }
    }
  }

  def addVideoToCache() = {

  }

}
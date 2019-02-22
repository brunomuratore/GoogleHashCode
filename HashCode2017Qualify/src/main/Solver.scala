/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.models.{Cache, Endpoint}

import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

class Solver(caches: ArrayBuffer[Cache], endpoints: ArrayBuffer[Endpoint]) {

  def solve(): List[Cache] = {

    endpoints.filter(_.links.nonEmpty).foreach { endpoint =>
      val closestCache = endpoint.links.minBy(_.latency).cache
      endpoint.requests.sortBy(-_.totalSize).map(_.video).foreach(closestCache.addIfHasSpace)
    }
    caches.toList
  }

}
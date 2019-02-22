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
      val closestCaches = endpoint.links.sortBy(_.latency).map(_.cache)

      endpoint.requests.sortBy(-_.totalSize).map(_.video).foreach{ video =>
        val it = closestCaches.iterator
        while(it.hasNext && !it.next().addIfHasSpace(video)){}
      }
    }
    caches.toList
  }



}
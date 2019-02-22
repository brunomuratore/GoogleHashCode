/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.{Cache, Endpoint, Video}

import scala.collection.mutable._
import util.control.Breaks._

class Solver(caches: ArrayBuffer[Cache], endpoints: ArrayBuffer[Endpoint], in: Int) {


  def solve(): List[Cache] = {

    val pb = new ProgressBar("Solver", endpoints.length)

    val videoSet = Set[Int]()

    endpoints.filter(_.links.nonEmpty).foreach { endpoint =>
      val closestCaches = endpoint.links.sortBy(_.latency).map(_.cache)

      endpoint.requests.sortBy(-_.totalSize).map(_.video).foreach{ video =>
        if(List(1,3).contains(in) || !videoSet.contains(video.id)) {
          val it = closestCaches.iterator
          while (it.hasNext && !it.next().addIfHasSpace(video)) {}
          videoSet += video.id
        }
      }

      pb.update()
    }

    caches.toList
  }



}
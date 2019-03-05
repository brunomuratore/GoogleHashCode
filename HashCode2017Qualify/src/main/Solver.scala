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
//    distributeVideosToCaches()
    // distributeVideosToMultipleCaches()

    // distributeVideosToMultipleCachesWithoutRepeatingVideos()
    caches
  }

//  def unCachedVideosForEndpoint(endpoint: Endpoint) = {
//    val allVideos = endpoint.requests.map(_._2.video).toSet
//    val cachedVideos = endpoint.caches.keys.flatMap(caches(_).cachedVideos.values).map(_.video).toSet
//
//    allVideos.diff(cachedVideos)
//  }
//
//  def distributeVideosToCaches() = {
//      val pb = new ProgressBar("Distributing", endpoints.size)
//
//      endpoints.foreach { endpoint =>
//        val unCachedVideos = unCachedVideosForEndpoint(endpoint)
//        val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1))
//        if (bestCaches.nonEmpty) {
//          endpoint.requests.filter(req => unCachedVideos.contains(req._2.video)).foreach { req =>
//            val savedLatency = (endpoint.latency - bestCaches.head.getLatencyForEndpointId(endpoint.id)) * req._2.totalSize
//            bestCaches.head.addToInfinityCache(req._2.video, savedLatency)
//          }
//        }
//        pb.update()
//      }
//
//    caches.foreach(_.trimCache())
//  }
//
//  def distributeVideosToMultipleCachesWithoutRepeatingVideos() = {
//    var cachedVideos = scala.collection.mutable.Set[Int]()
//
//    endpoints.foreach { endpoint =>
//      val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1)).toArray
//      var iCache = 0
//      if (bestCaches.nonEmpty) {
//        endpoint.requests.foreach { req =>
//          if (cachedVideos.contains(req._2.video.id) == false) {
//            if (iCache < bestCaches.length) {
//              val savedLatency = (endpoint.latency - bestCaches(iCache).getLatencyForEndpointId(endpoint.id)) * req._2.totalSize
//              var added = bestCaches(iCache).addIfHasSpace(req._2.video, savedLatency)
//              if (added == false) {
//                iCache = iCache + 1
//                if (iCache < bestCaches.length) {
//                  bestCaches(iCache).addIfHasSpace(req._2.video, savedLatency)
//                }
//              }
//              if (added == true) {
//                cachedVideos += req._2.video.id
//              }
//            }
//          }
//        }
//      }
//    }
//
//    caches.foreach(_.trimCache())
//  }
//
//  def distributeVideosToMultipleCaches() = {
//    endpoints.foreach { endpoint =>
//      val bestCaches = endpoint.caches.toList.sortBy(_._2).map(kv => caches(kv._1))
//      var iCache = 0
//      if (bestCaches.nonEmpty) {
//        endpoint.requests.foreach { req =>
//          if (iCache < bestCaches.length) {
//            val savedLatency = (endpoint.latency - bestCaches(iCache).getLatencyForEndpointId(endpoint.id)) * req._2.totalSize
//            val added = bestCaches(iCache).addIfHasSpace(req._2.video, savedLatency)
//            if (added == false) {
//              iCache = iCache + 1
//              if (iCache < bestCaches.length) {
//                bestCaches(iCache).addIfHasSpace(req._2.video, savedLatency)
//              }
//            }
//          }
//        }
//      }
//    }
//
//    caches.foreach(_.trimCache())
//  }


}

/*
total: 1601000 ▲ 149000
videos_worth_spreading.in: 422000 ▲ 253000
me_at_the_zoo.in: 318000 ▼ -4000
kittens.in: 524000 ▼ -80000
trending_today.in: 337000 ▼ -134000
*/

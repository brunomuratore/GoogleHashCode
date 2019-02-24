package main.scorer

import main.framework.{ProgressBar, Score}
import main.models.{Cache, Endpoint}

import scala.collection.mutable.ArrayBuffer

object Scorer {
  def compute(endpoints: ArrayBuffer[Endpoint], caches: ArrayBuffer[Cache]): Score = {
    val max = 0

    var totalSaved = 0L
    var totalRequests = 0L

    val pb = new ProgressBar("Score", endpoints.size)
    endpoints.foreach{ end =>
      val cachesForEnd = end.caches.keys.map(caches(_))
      end.requests.foreach{ req =>
        totalSaved += req.qty *
          (end.latency -
            cachesForEnd.filter(_.cachedVideos.contains(req.video.id))
                  .map(cache => cache.getLatencyForEndpointId(end.id))
                  .toList.sorted.headOption.getOrElse(end.latency))
        totalRequests += req.qty
      }
      pb.update()
    }

//    val totalSaved = caches.map(_.cachedVideos.values.map(_.savedLatency).sum).sum
//    val totalRequests = endpoints.map(_.requests.length).sum

    Score(totalSaved / totalRequests * 1000, max)
  }
}

//end.caches.keys.map(caches(_))
// 1 -> 203, 3 ->192
// 1, 3
// caches(1), caches(3)
// caches(3)

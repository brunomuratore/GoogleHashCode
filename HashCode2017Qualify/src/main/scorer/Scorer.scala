package main.scorer

import main.framework.{ProgressBar, Score}
import main.models.{Cache, Endpoint}

import scala.collection.mutable.ArrayBuffer

object Scorer {
  def compute(endpoints: ArrayBuffer[Endpoint], caches: ArrayBuffer[Cache]): Score = {
    val max = 0

    var totalSaved = 0
    var totalRequests = 0

    val pb = new ProgressBar("Score", endpoints.size)
    endpoints.foreach{ end =>
      end.requests.foreach{ req =>
        totalSaved += req.qty *
          (end.latency -
                end.caches.keys.filter(
                  cacheId =>  caches(cacheId).cachedVideos.contains(req.video.id)
                ).map(cacheId => caches(cacheId).getLatencyForEndpointId(end.id)).toList.sorted.headOption.getOrElse(end.latency))
        totalRequests += req.qty
      }
      pb.update()
    }

//    val totalSaved = caches.map(_.cachedVideos.values.map(_.savedLatency).sum).sum
//    val totalRequests = endpoints.map(_.requests.length).sum

    Score(totalSaved / totalRequests * 1000, max)
  }
}

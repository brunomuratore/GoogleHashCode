package main.scorer

import main.framework.{ProgressBar, Score}
import main.models.{Cache, Endpoint, Requests}

import scala.collection.mutable.ArrayBuffer

object Scorer {

  def getBestLatency(end: Endpoint, req: Requests): Int = {
    end.bestCaches.foreach { c=>
      if (c.cachedVideos.contains(req.video.id)) {
        return c.getLatencyForEndpointId(end.id)
      }
    }
    return end.latency
  }

  def compute(endpoints: ArrayBuffer[Endpoint], caches: ArrayBuffer[Cache]): Score = {
    val max = 0

    var totalSaved = 0L
    caches.foreach {c =>
      c.cachedVideos.foreach{v =>
        totalSaved += v._2.savedLatency
      }
    }
    var totalRequests = 0L

    val pb = new ProgressBar("Score", endpoints.size)
    endpoints.foreach{ end =>
      end.requests.values.foreach { req =>
        //totalSaved += req.qty * (end.latency - getBestLatency(end, req))
        totalRequests += req.qty
      }
      pb.update()
    }

    Score(totalSaved / totalRequests * 1000, max)
  }
}

//end.caches.keys.map(caches(_))
// 1 -> 203, 3 ->192
// 1, 3
// caches(1), caches(3)
// caches(3)

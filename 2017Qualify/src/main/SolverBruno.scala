package main

import main.framework.Param._
import main.framework._
import main.models._

import scala.collection.mutable._
import scala.util.Random

class SolverBruno(caches: ArrayBuffer[Cache], endpoints: ArrayBuffer[Endpoint])(implicit file: String) {

  def solve(): ArrayBuffer[Cache] = {

    val videoSet = Set[Int]()
    val endpointsPerVideo = HashMap[Video, ListBuffer[Endpoint]]()

    // Calculate best Caches per Endpoint
    // Fill map of endpoints per Video
    endpoints.foreach{e =>
      e.bestCaches = e.caches.toList.sortBy(kv => kv._2).map(kv =>caches(kv._1))
      e.requests.foreach{r =>
       endpointsPerVideo(r._2.video) = endpointsPerVideo.getOrElse(r._2.video, ListBuffer.empty) :+ e
      }
      e.bestVideos = e.requests.values.toList.sortBy(-videoScore(_)).map(_.video)
    }

    // Initially populate caches by getting the videos with most "score" of each endpoint
    // and populating all caches connected to the endpoint until they are full
    var pb = new ProgressBar("Solver", endpoints.length)
    endpoints.filter(_.caches.nonEmpty).foreach { endpoint =>
      endpoint.bestVideos.foreach{ video =>
        if(repeatVideo || !videoSet.contains(video.id)) {
          val it = endpoint.bestCaches.iterator
          while (it.hasNext && !it.next().addIfHasSpace(video)) {}
          videoSet += video.id
        }
      }
      pb.update()
    }

    // Calculate savedLatency for each video stored on each cache
    val counted = HashSet[(Int, Int)]()
    caches.foreach{c =>
      c.cachedVideos.values.foreach{v =>
        var savedLatency = 0
        endpointsPerVideo(v.video).foreach{ e =>
          if (!counted.contains((v.video.id, e.id)) && e.caches.contains(c.id)) {
            savedLatency += (e.latency - e.caches(c.id)) * e.requests(v.video.id).qty
            counted += ((v.video.id, e.id))
          }
        }
        v.savedLatency = savedLatency
      }
    }

    // Randomly get an endpoint and try to fit its "best" video into a cache
    // Verify if with this new video the score will be improved
//    pb = new ProgressBar("Random improve", endpoints.length)
//    0.to(100).foreach{ _ =>
//      val r = Random.nextInt(endpoints.length)
//      val e = endpoints(r)
//      // get random video from top 50%
//      val v = e.bestVideos(Random.nextInt(e.bestVideos.length/2))
//      // if video is not in cache, calculate if score would increase if we added it to cache
//      if (!e.bestCaches.exists(_.cachedVideos.contains(v.id))) {
//        val replaceVideo = e.bestCaches.head.cachedVideos
//      }
//      pb.update()
//    }

    caches
  }
}

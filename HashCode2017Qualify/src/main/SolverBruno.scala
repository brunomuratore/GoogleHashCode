package main

import main.framework.Param._
import main.framework._
import main.models._
import scala.collection.mutable._

class SolverBruno(caches: ArrayBuffer[Cache], endpoints: ArrayBuffer[Endpoint])(implicit file: String) {

  def solve(): ArrayBuffer[Cache] = {

    val videoSet = Set[Int]()
    val endpointsPerVideo = HashMap[Video, ListBuffer[Endpoint]]()

    endpoints.foreach{e =>
      e.bestCaches = e.caches.toList.sortBy(kv => kv._2).map(kv =>caches(kv._1))
//      e.requests.foreach{r =>
//        endpointsPerVideo(r.video) = endpointsPerVideo.getOrElse(r.video, ListBuffer.empty) :+ e
//      }
    }

    val pb = new ProgressBar("Solver", endpoints.length)
    endpoints.filter(_.caches.nonEmpty).foreach { endpoint =>
      endpoint.requests.sortBy(-videoScore(_)).map(_.video).foreach{ video =>
        if(repeatVideo || !videoSet.contains(video.id)) {
          val it = endpoint.bestCaches.iterator
          while (it.hasNext && !it.next().addIfHasSpace(video)) {}
          videoSet += video.id
        }
      }

      pb.update()
    }



    caches
  }
}

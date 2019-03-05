package main.io

import java.io._

import main.models.Cache

import scala.collection.mutable.ArrayBuffer


object OutputWriter {

  def write(fileName: String, caches: ArrayBuffer[Cache]): Unit = {
    val file = new File(s"resources/output/$fileName")
    file.getParentFile.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file.getAbsolutePath))
    val cachesWithVideo = caches.filter(_.cachedVideos.nonEmpty)

    bw.write(cachesWithVideo.length + "\n")

    cachesWithVideo.foreach{ cache =>
      bw.write(s"${cache.id} ${cache.cachedVideos.keys.mkString(" ")}\n")
    }

    bw.close()
  }
}

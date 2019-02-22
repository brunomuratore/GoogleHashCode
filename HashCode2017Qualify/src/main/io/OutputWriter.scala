package main.io

import java.io._

import main.models.Cache


object OutputWriter {

  def write(fileName: String, caches: List[Cache]): Unit = {
    val file = new File(s"resources/output/$fileName")
    file.getParentFile.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file.getAbsolutePath))
    val cachesWithVideo = caches.filter(_.videos.nonEmpty)

    bw.write(cachesWithVideo.length + "\n")

    cachesWithVideo.foreach{ cache =>
      bw.write(s"${cache.id} ${cache.videos.map(_._2.id).mkString(" ")}\n")
    }

    bw.close()
  }
}

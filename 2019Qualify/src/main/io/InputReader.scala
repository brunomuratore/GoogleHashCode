package main.io

import java.io.{BufferedReader, File, FileReader}

import main.framework.ProgressBar
import main.models.Photo

import scala.collection.mutable._

object InputReader {

  def read(file: String): Array[Photo] = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    val rows = reader.readLine().toInt

    val photos = Array.ofDim[Photo](rows)

    val pb = new ProgressBar("Input", rows)

    0.until(rows).foreach{i =>
      val line = reader.readLine().split(" ")
      val id = i
      val vertical = line(0) == "V"
      val tags = line.slice(2, line.length)
      val photo = Photo(id, vertical, tags)
      photos(i) = photo
      pb.update()
    }

    reader.close()

    photos
  }
}

package main.io

import java.io.{BufferedReader, File, FileReader}

import main.models.Photo

import scala.collection.mutable

object InputReader {

  def read(file: String): (mutable.Set[Photo], mutable.Map[String, mutable.Set[Photo]]) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    val rows = reader.readLine().toInt

    val photos = mutable.Set.empty[Photo]
    val tagToPhotos = mutable.Map.empty[String, mutable.Set[Photo]].withDefault(_ => mutable.Set.empty[Photo])

    0.until(rows).foreach(_ => {
      val line = reader.readLine().split(" ")
      val id = line(0).toInt
      val vertical = line(1) == "V"
      val tags = line.takeRight(2).toSet
      val photo = Photo(id, vertical, tags)
      photos.add(photo)
      tags.foreach(tag => {
        tagToPhotos(tag).add(photo)
      })
    })

    reader.close()

    (photos, tagToPhotos)
  }
}

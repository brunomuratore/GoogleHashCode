package main.io

import java.io.{BufferedReader, File, FileReader}

import main.models.Photo

import scala.collection.mutable._

object InputReader {

  def read(file: String): (Map[Photo, Photo], Map[String, Map[Photo, Photo]], TreeMap[Int,
    Map[Photo, Photo]]) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    val rows = reader.readLine().toInt

    val photos = Map.empty[Photo, Photo]
    val tagToPhotos = Map.empty[String, Map[Photo, Photo]]
    val sortedPhotos = TreeMap[Int, Map[Photo, Photo]]()

    0.until(rows).foreach(_ => {
      val line = reader.readLine().split(" ")
      val id = line(1).toInt
      val vertical = line(0) == "V"
      val tags = line.slice(2, line.length).toSet
      val photo = Photo(id, vertical, tags)
      photos += photo -> photo

      tags.foreach(tag => {
        if (tagToPhotos.contains(tag)) {
          tagToPhotos(tag) += photo -> photo
        } else {
          tagToPhotos(tag) = Map(photo -> photo)
        }
      })

      val num = photo.tags.size
      if (sortedPhotos.contains(num)) {
        sortedPhotos(num) += photo -> photo
      } else {
        sortedPhotos(num) = Map(photo -> photo)
      }
    })

    reader.close()

    (photos, tagToPhotos, sortedPhotos)
  }
}

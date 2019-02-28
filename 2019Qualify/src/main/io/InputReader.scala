package main.io

import java.io.{BufferedReader, File, FileReader}

import main.models.Photo

import scala.collection.mutable

object InputReader {

  def read(file: String): (mutable.Set[Photo], mutable.Map[String, Array[Photo]], mutable.TreeMap[Int, Array[Photo]]) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    val rows = reader.readLine().toInt

    val photos = mutable.Set.empty[Photo]
    val tagToPhotos = mutable.Map.empty[String, Array[Photo]]
    val sortedPhotos = mutable.TreeMap[Int, Array[Photo]]()

    0.until(rows).foreach(_ => {
      val line = reader.readLine().split(" ")
      val id = line(1).toInt
      val vertical = line(0) == "V"
      val tags = line.slice(2, line.length).toSet
      val photo = Photo(id, vertical, tags)
      photos.add(photo)

      tags.foreach(tag => {
        if (tagToPhotos.contains(tag)) {
          tagToPhotos(tag) = tagToPhotos(tag) :+ photo
        } else {
          tagToPhotos(tag) = Array(photo)
        }
      })

      val num = photo.tags.size
      if (sortedPhotos.contains(num)) {
        sortedPhotos(num) = sortedPhotos(num) :+ photo
      } else {
        sortedPhotos(num) = Array(photo)
      }
    })

    reader.close()

    (photos, tagToPhotos, sortedPhotos)
  }
}

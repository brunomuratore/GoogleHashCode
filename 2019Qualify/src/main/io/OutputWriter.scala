package main.io

import java.io._

import main.models.SlideShow

object OutputWriter {

  def write(slideShow: SlideShow, fileName: String): Unit = {
    val file = new File(s"resources/output/$fileName")
    file.getParentFile.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file.getAbsolutePath))

    bw.write(slideShow.slides.length + "\n")
    slideShow.slides.foreach(slide => {
      bw.write(slide.photos.map(p => p.id).mkString(" ") + "\n")
    })

    bw.close()
  }

}

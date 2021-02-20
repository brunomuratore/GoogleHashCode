package main.io

import java.io._

import main.models.Slice

object OutputWriter {

  def write(slices: Int, fileName: String): Unit = {
    val file = new File(s"resources/output/$fileName")
    file.getParentFile.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file.getAbsolutePath))
    bw.write(0 + "\n")

    bw.close()
  }
}

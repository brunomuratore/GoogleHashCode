package main.io

import java.io._


object OutputWriter {

  def write(fileName: String): Unit = {
    val file = new File(s"resources/output/$fileName")
    file.getParentFile.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file.getAbsolutePath))
    //bw.write(slices.size + "\n")

    bw.close()
  }
}

package main.io

import java.io._


object OutputWriter {

  def write(fileName: String): Unit = {
    val file = new File(s"resources/output/$fileName").getAbsolutePath
    val bw = new BufferedWriter(new FileWriter(file))
    //bw.write(slices.size + "\n")

    bw.close()
  }
}

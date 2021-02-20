package main

import java.io._

object OutputWriter {

  def write(fileName: String, vehicles: Array[Vehicle]): Unit = {
    val file = new File(s"resources/output/$fileName")
    if (!file.exists()) {
      file.getParentFile.mkdirs()
      file.createNewFile()
    }
    val bw = new BufferedWriter(new FileWriter(file))
    vehicles.foreach(v => {
      bw.write(s"${v.rides.size}")
      v.rides.foreach(r => bw.write(s" ${r.id}"))
      bw.write("\n")
    })
    bw.close()
  }
}

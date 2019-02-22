package main.io

import java.io._

import main.models.Slice

object OutputWriter {

  def write(slices: Iterable[Slice], fileName: String): Unit = {
    val file = new File(s"resources/output/$fileName")
    file.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file.getAbsolutePath))
    bw.write(slices.size + "\n")
    slices.foreach(s => bw.write(s"${s.p1.row} ${s.p1.col} ${s.p2.row} ${s.p2.col}\n"))
    bw.close()
  }
}

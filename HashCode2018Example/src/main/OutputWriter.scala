package main

import java.io._

import scala.collection.mutable.ListBuffer

object OutputWriter {

  def write(slices: ListBuffer[Slice], fileName: String): Unit = {
    val file = new File(s"resources/output/$fileName").getAbsolutePath
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(slices.size + "\n")
    slices.foreach(s => bw.write(s"${s.p1.x} ${s.p1.y} ${s.p2.x} ${s.p2.y}\n"))
    bw.close()
  }
}

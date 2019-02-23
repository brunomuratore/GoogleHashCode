package main.io

import java.io.{BufferedReader, File, FileReader}

object InputReader {
  def read(file: String): (Array[Array[Int]], Int, Int) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))


    val a = Array.ofDim[Int](1, 1)

    reader.close()

    (a, 0, 0)
  }
}

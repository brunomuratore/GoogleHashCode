package main.io

import java.io.{BufferedReader, File, FileReader}

object InputReader {
  def read(file: String): (Array[Array[Int]], Int, Int) = {
    val path = new File(s"resources/input/$file").getAbsolutePath
    val reader = new BufferedReader(new FileReader(path))

    // CHANGE HERE: Read input file
    val line = reader.readLine().split(" ")
    val rows = line(0).toInt
    val cols = line(1).toInt
    val min = line(2).toInt
    val max = line(3).toInt

    val a = Array.ofDim[Int](rows, cols)

    0.until(rows).foreach(r => {
      val toppings = reader.readLine().toCharArray
      0.until(cols).foreach(c => {
        a(r)(c) = toppings(c) match {
          case 'T' => 0
          case 'M' => 1
        }
      })
    })

    reader.close()

    (a, min, max)
  }
}

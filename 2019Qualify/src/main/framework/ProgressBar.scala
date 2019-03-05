package main.framework

class ProgressBar(name: String, total: Float) {

  def space = {
    val sb =  new StringBuilder()
    0.to(30 - name.length).foreach(_ => sb.append("-"))
    sb.toString
  }

  print(s"$name $space [")
  private var current = 0f
  private var currentBars = 0
  private val totalBars = 20
  def update(amount: Int = 1) {
    current += amount
    val bars = (current / total * totalBars).toInt
    currentBars.until(bars).foreach(_=>print("#"))
    currentBars = bars
    if (bars == totalBars) {
      print("]\n")
      current = 0
    }

  }
}

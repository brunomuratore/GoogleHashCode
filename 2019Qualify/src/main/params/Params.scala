package main.params

object Params {
  def isTestCaseE(implicit file: String) = file match {
    case "e_shiny_selfies.txt" => true
    case _ => false
  }
}

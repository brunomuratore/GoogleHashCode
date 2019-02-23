package main.params

object Params {
  def numOfSlicesToRemove(implicit file: String) = file match {
    case "small.in" => 3
    case "medium.in" => 2
    case _ => 1
  }
}

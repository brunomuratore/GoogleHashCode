package main.framework

import main.models.{Requests, Video}

object Param {
  def repeatVideo(implicit file: String) = file match {
    case "trending_today.in" => false
    case "kittens.in" => false
    case _ => true
  }

  def videoScore(r: Requests)(implicit file: String) = file match {
    case "kittens.in" => r.qty / r.video.size
    case _ => r.qty
  }
}

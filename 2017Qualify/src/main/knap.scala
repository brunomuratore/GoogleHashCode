package main

import scala.collection.mutable.Set

object knap {

  case class Item(videoId: Int, weight: Int, value: Int)

  var result = Set[Int]()
  var W = 0
  //===== dynamic programming ==========================================================
  val knap: List[Item] => Unit = loi => {
    val N = loi.size

    val m = Array.ofDim[Int](N+1,W+1)
    val plm = (List((for {w <- 0 to W} yield Set[Item]()).toArray)++(
      for {
        n <- 0 to N-1
        colN = (for {w <- 0 to W} yield Set[Item](loi(n))).toArray
      } yield colN)).toArray

    1 to N foreach {n =>
      0 to W foreach {w =>
        def in = loi(n-1)
        def wn = loi(n-1).weight
        def vn = loi(n-1).value
        if (w<wn) {
          m(n)(w) = m(n-1)(w)
          plm(n)(w) = plm(n-1)(w)
        }
        else {
          if (m(n-1)(w)>=m(n-1)(w-wn)+vn) {
            m(n)(w) = m(n-1)(w)
            plm(n)(w) = plm(n-1)(w)
          }
          else {
            m(n)(w) = m(n-1)(w-wn)+vn
            plm(n)(w) = plm(n-1)(w-wn)+in
          }
        }
      }
    }

    result = plm(N)(W).map(_.videoId)
  }

  def run(items: List[Item], maxSize: Int): Set[Int] = {
    result = Set[Int]()
    W = maxSize
    knap(items)
    result
  }
}
/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.framework.ProgressBar
import main.models.Slice

import scala.util.Random

class Solver(pizza: Array[Array[Int]], minOfEach: Int, maxSize: Int)(implicit file: String) {
  val r = new Random()

  def solve() = {

    run()
    List[Slice]()
  }

  private def run() = {
    val bar = new ProgressBar("Greedy solve", 1)

    bar.update()
  }



}
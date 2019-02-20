/*
 * Copyright (c) 2017 Schibsted Media Group. All rights reserved.
 */

package main

import main.models.{Cache, Endpoint}

import scala.collection.mutable.ArrayBuffer

class Solver(caches: ArrayBuffer[Cache], endpoints: ArrayBuffer[Endpoint]) {

  def solve(): List[Cache] = {

    caches.toList
  }

}
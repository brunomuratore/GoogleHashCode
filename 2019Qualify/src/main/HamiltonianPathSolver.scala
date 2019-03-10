package main

import main.framework.ProgressBar
import main.models.{Photo, Slide, SlideShow}
import main.scorer.Scorer

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer, Queue}
import scala.util.Random

class HamiltonianPathSolver(photos: Array[Photo])(implicit file: String) {
  val slideShow = SlideShow(ListBuffer.empty)
  val edges = findEdges()
  def findEdges(): Map[Integer, Set[Integer]] = {
    val map = mutable.Map[String, ListBuffer[Photo]]()
    val edges = mutable.Map[Integer, mutable.Set[Integer]]()

    val pb = new ProgressBar("Calculate Edges", photos.length)

    photos.foreach{p =>
      p.tags.foreach{t =>
        if (!map.contains(t)) {
          map(t) = ListBuffer(p)
        } else {
          map(t).foreach{existingPhoto =>
            if(!edges.contains(existingPhoto.id)) edges(existingPhoto.id) = mutable.Set.empty
            edges(existingPhoto.id) += p.id
            if(!edges.contains(p.id)) edges(p.id) = mutable.Set.empty
            edges(p.id) += existingPhoto.id
          }
          map(t) += p
        }
      }
      pb.update()
    }
    edges.map(kv => kv._1 -> kv._2.toSet).toMap
  }

  var it = 0
  var end = false
  def ended: Boolean = {
    if (end) return end
    it += 1
    if (it == 1000) {
      it = 0
      val diff: Long = System.nanoTime() - startTime
      if(diff > 60000000000L) {
        end = true
        true
      }
      else false
    } else {
      false
    }
  }

  def solveDFS(path: mutable.Stack[Integer], start: Int): Unit = {
    if(ended) return
    var end = true
    edges(start).foreach{e =>
      if(!visited.contains(e)) {
        path.push(e)
        visited += e
        solveDFS(path, e)
        path.pop()
        visited -= e
        end = false
      }
    }
    if(end && path.size > bestPath.size) {
      bestPath = path.clone()
    }
  }

  var bestPath = mutable.Stack[Integer]()
  val visited = mutable.Set[Integer]()
  var startTime = System.nanoTime()
  def solve(edges: Map[Integer, Set[Integer]]) = {
    var size = 0
    val pb = new ProgressBar("Hamilt Solver", edges.size)
    (edges.size-1).to(0).by(-1).foreach{start =>
      if(!visited.contains(start)) {
        val path = mutable.Stack[Integer]()
        path.push(start)
        visited += start
        bestPath.clear()

        startTime = System.nanoTime()
        end = false
        solveDFS(path, start)

        bestPath.foreach { s =>
          slideShow.slides += Slide.of(photos(s))
          visited += s
        }

        println(start)
        println("best path size: " + bestPath.size)
        size += bestPath.size
      }
      pb.update()
    }
    println("total Size = " + size)
  }

  def run() = {

    val path = solve(edges)

    slideShow
  }

}
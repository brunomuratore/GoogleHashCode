package main

import main.framework.Utils.time
import main.models.{Photo, SlideShow}
import main.params.Params
import main.params.Params.SolverType

class Solver(photos: Array[Photo])(implicit file: String) {

  def solve(): SlideShow = {
    val slidesShow = time { run() }
    slidesShow
  }

  private def run(): SlideShow = {
    Params.getSolver match {
      case SolverType.GreedyPhoto => new GreedyPhotoSolver(photos).run()
      case SolverType.GreedySlide => new GreedySlideSolver(photos).run()
      case SolverType.HamiltonianPath => new HamiltonianPathSolver(photos).run()
    }
  }

}
using HashCode2020;
using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace HashCode2021
{
    internal partial class Solver
    {
        private readonly string file;

        public Solver(string file)
        {
            this.file = file;
        }

        public Result Solve(Model m)
        {
            return file switch
            {
                "a.txt" => SolveGeneric(m),
                _ => SolveGeneric(m),
            };
        }

        public Result SolveGeneric(Model m)
        {
            // ================ CUSTOM SOLVER START =========================            
            var bestLibraries = m.libraries.Values.OrderByDescending(l => l.maxPotentialScore).ToList();
            var resultLibraries = new List<Street>();




            return new Result(resultLibraries, m.days);
            // ================ CUSTOM SOLVER END =========================
        }

        // Use in case of expensive loops
        //var timer = new Stopwatch();
        //timer.Start();
        //while(condition && timer.ElapsedMilliseconds< 1000 * 5)
        //{

        //}
    }
}
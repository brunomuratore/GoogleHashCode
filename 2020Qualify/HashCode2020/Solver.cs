using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HashCode2021
{
    internal class Solver
    {

        public Solver()
        {
        }

        public Result Solve(Model m)
        {
            Console.WriteLine($"days: {m.days}, books: {m.books.Count}, libraries: {m.libraries.Count}");

            var bestLibraries = m.libraries.Values.OrderByDescending(l => l.maxPotentialScore).ToList();
            
            foreach(var l in bestLibraries)
            {
                l.Dedup();
            }

            bestLibraries = bestLibraries.OrderByDescending(l => l.maxPotentialScore).ToList();

            return new Result(bestLibraries, m.days);
        }

        public class Result
        {
            public List<Library> libraries;
            public int days;

            public Result(List<Library> l, int days)
            {
                this.libraries = l;
                this.days = days;
            }
        }
    }
}
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
               


            return new Result(m.libraries.First().Value, m.days);
        }

        public class Result
        {
            public List<Library> libraries;
            public int days;

            public Result(Library l, int days)
            {
                this.libraries = new List<Library>() { l };
                this.days = days;
            }
        }
    }
}
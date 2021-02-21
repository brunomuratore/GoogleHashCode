using HashCode2020;
using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HashCode2021
{
    class Program
    {
        private static readonly HashSet<string> files = new HashSet<string> { "a.txt", "b.txt", "c.txt", "d.txt", "e.txt", "f.txt" };
        //private static readonly HashSet<string> files = { "a.txt" };

        static void Main(string[] args)
        {
            Console.WriteLine("HashCode 2021 - Br = Hu3^2\n");
            var ratingService = new Rating(files);

            foreach (var file in files)
            {
                Console.WriteLine($"Processing {file}");

                var model = InputReader.Read(file);
                Console.WriteLine($"Read input");

                // Reset globals
                Global.UsedBooks = new HashSet<Book>();
                Global.RemainingDays = Global.Days;

                var solver = new Solver();

                var result = solver.Solve(model);
                Console.WriteLine($"Calculated result");

                var score = ratingService.Calculate(file, result);
                Console.WriteLine($"Calculated score: {score}");

                var outputFile = OutputWriter.Write(file, result, ratingService);
                Console.WriteLine($"Generated output: {outputFile}");
                Console.WriteLine();
            }
            Utils.WriteSummary();

            Console.ReadLine();
        }
    }
}

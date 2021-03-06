using HashCode2020;
using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace HashCode2021
{
    class Program
    {
        //private static readonly HashSet<string> files = new HashSet<string> { "a.txt", "b.txt", "c.txt", "d.txt" };
        private static readonly HashSet<string> files = new HashSet<string> { "a.txt", "b.txt", "c.txt", "d.txt", "e.txt", "f.txt" };
        //private static readonly HashSet<string> files = new HashSet<string> { "e.txt", "f.txt" };
        //private static readonly HashSet<string> files = new HashSet<string> { "d.txt" };

        static void Main(string[] args)
        {
            var watch = new Stopwatch();
            watch.Start();

            Console.WriteLine("HashCode 2021 - Br = Hu3^2\n");
            var ratingService = new Rating(files);

            var iteration = 1;
            while (iteration-- > 0 )
            {
                L.LogA($"#{iteration}");

                //Parallel.ForEach(files, file =>
                foreach (var file in files)
                {
                    L.Log($"Processing {file}");

                    var model = InputReader.Read(file);
                    L.Log($"Read input");

                    var solver = new Solver(file);
                    
                    var result = solver.Solve(model);
                    L.Log($"Calculated result");

                    var score = ratingService.Calculate(file, result);
                    L.Log($"Calculated score: {score}");

                    var outputFile = OutputWriter.Write(file, result, ratingService);
                    L.Log($"Generated output: {outputFile}\n");
                }

                Utils.WriteSummary();
            }

            watch.Stop();
            Console.WriteLine($"Finished in {watch.ElapsedMilliseconds}ms");

            Process.Start(@"../../../zip.bat");

            Console.ReadLine();
        }        
    }

    public static class L
    {
        static readonly bool enabled = true;
        public static void Log(string msg)
        {
            if (enabled) Console.WriteLine(msg);
        }
        public static void LogA(string msg)
        {
            Console.WriteLine(msg);
        }
    }
}

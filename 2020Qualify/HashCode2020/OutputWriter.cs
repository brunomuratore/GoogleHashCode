using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using static HashCode2021.Solver;

namespace HashCode2021
{
    internal static class OutputWriter
    {
        internal static string Write(string file, Result result, Rating rating)
        {
            var lines = new[] { "1", "2" };


            return Save(file, lines, rating);
        }

        private static string Save(string file, string[] lines, Rating rating)
        {
            var path = $"../../../output/";
            var fileName = $"{path}{file}";

            if (!Directory.Exists(path)) Directory.CreateDirectory(path);

            if (rating.GetNewBest().Contains(file)) File.WriteAllLines(fileName, lines);

            return fileName;
        }
    }
}
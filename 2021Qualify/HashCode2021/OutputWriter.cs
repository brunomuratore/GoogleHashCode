using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using static HashCode2021.Solver;

namespace HashCode2021
{
    internal static class OutputWriter
    {
        internal static string Write(string file, Result r, Rating rating)
        {
            var lines = new List<string>();

            // ================ CUSTOM OUTPUT WRITE START =========================
            // Just fill "lines" variable with each line to be outputed on file

            lines.Add(r.libraries.Count.ToString());

            foreach(var library in r.libraries)
            {
                lines.Add($"{library.id} {library.scan.Count}");

                lines.Add(string.Join(" ", library.scan.Select(book => book.id)));
            }

            // ================ CUSTOM OUTPUT WRITE END =========================

            return Save(file, lines, rating);
        }

        private static string Save(string file, List<string> lines, Rating rating)
        {
            var path = $"../../../output/";
            var fileName = $"{path}{file}";

            if (!Directory.Exists(path)) Directory.CreateDirectory(path);

            if (rating.GetNewBest().Contains(file)) File.WriteAllLines(fileName, lines);

            return fileName;
        }
    }
}
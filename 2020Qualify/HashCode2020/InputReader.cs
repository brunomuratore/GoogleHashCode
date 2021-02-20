using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Linq;

namespace HashCode2021
{
    public static class InputReader
    {
        public static string Read(string file)
        {
            var lines = File.ReadAllLines($"../../../input/{file}");

            foreach (var line in lines)
            {

            }

            return lines.Length.ToString();
        }
    }
}

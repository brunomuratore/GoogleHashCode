using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;

namespace HashCode2021
{
    public static class Utils
    {
        /// <summary>
        ///  Writes to Console output. Words under [brackets] will be colored
        /// </summary>
        /// <param name="message">String to be sent to output</param>
        /// <param name="color">Color to use on words under [brackets]</param>
        public static void WriteColor(string message, ConsoleColor color)
        {
            var pieces = Regex.Split(message, @"(\[[^\]]*\])");

            for (int i = 0; i < pieces.Length; i++)
            {
                string piece = pieces[i];

                if (piece.StartsWith("[") && piece.EndsWith("]"))
                {
                    Console.ForegroundColor = color;
                    piece = piece.Substring(1, piece.Length - 2);
                }

                Console.Write(piece);
                Console.ResetColor();
            }

            Console.WriteLine();
        }

        private static List<(string, ConsoleColor)> summaryEntries = new List<(string, ConsoleColor)>();
        public static void AddSummary(string message, ConsoleColor color)
        {
            summaryEntries.Add((message, color));
        }
        public static void WriteSummary()
        {
            Console.WriteLine();
            summaryEntries.ForEach(e => WriteColor(e.Item1, e.Item2));
        }

        #region Splits
        public static (A, B) Split2<A, B>(this string s, string separator)
        {
            var sp = s.Split(separator);
            return ((A)Convert.ChangeType(sp[0], typeof(A)), (B)Convert.ChangeType(sp[1], typeof(B)));
        }
        public static (long, long) Split2(this string s, string separator)
        {
            var sp = s.Split(separator);
            return (long.Parse(sp[0]), long.Parse(sp[1]));
        }
        public static (A, B, C) Split3<A, B, C>(this string s, string separator)
        {
            var sp = s.Split(separator);
            return ((A)Convert.ChangeType(sp[0], typeof(A)), (B)Convert.ChangeType(sp[1], typeof(B)), (C)Convert.ChangeType(sp[2], typeof(C)));
        }
        public static (long, long, long) Split3(this string s, string separator)
        {
            var sp = s.Split(separator);
            return (long.Parse(sp[0]), long.Parse(sp[1]), long.Parse(sp[2]));
        }
        public static (A, B, C, D) Split4<A, B, C, D>(this string s, string separator)
        {
            var sp = s.Split(separator);
            return ((A)Convert.ChangeType(sp[0], typeof(A)), (B)Convert.ChangeType(sp[1], typeof(B)), (C)Convert.ChangeType(sp[2], typeof(C)), (D)Convert.ChangeType(sp[3], typeof(D)));
        }
        public static (long, long, long, long) Split4(this string s, string separator)
        {
            var sp = s.Split(separator);
            return (long.Parse(sp[0]), long.Parse(sp[1]), long.Parse(sp[2]), long.Parse(sp[3]));
        }
        public static (A, B, C, D, E) Split5<A, B, C, D, E>(this string s, string separator)
        {
            var sp = s.Split(separator);
            return ((A)Convert.ChangeType(sp[0], typeof(A)), (B)Convert.ChangeType(sp[1], typeof(B)), (C)Convert.ChangeType(sp[2], typeof(C)), (D)Convert.ChangeType(sp[3], typeof(D)), (E)Convert.ChangeType(sp[4], typeof(E)));
        }
        public static (long, long, long, long, long) Split5(this string s, string separator)
        {
            var sp = s.Split(separator);
            return (long.Parse(sp[0]), long.Parse(sp[1]), long.Parse(sp[2]), long.Parse(sp[3]), long.Parse(sp[4]));
        }
        public static (A, B, C, D, E, F) Split6<A, B, C, D, E, F>(this string s, string separator)
        {
            var sp = s.Split(separator);
            return ((A)Convert.ChangeType(sp[0], typeof(A)), (B)Convert.ChangeType(sp[1], typeof(B)), (C)Convert.ChangeType(sp[2], typeof(C)), (D)Convert.ChangeType(sp[3], typeof(D)), (E)Convert.ChangeType(sp[4], typeof(E)), (F)Convert.ChangeType(sp[5], typeof(F)));
        }
        public static (long, long, long, long, long, long) Split6(this string s, string separator)
        {
            var sp = s.Split(separator);
            return (long.Parse(sp[0]), long.Parse(sp[1]), long.Parse(sp[2]), long.Parse(sp[3]), long.Parse(sp[4]), long.Parse(sp[5]));
        }
        #endregion
    }
}

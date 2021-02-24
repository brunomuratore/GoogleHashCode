using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Linq;
using HashCode2020.models;
using HashCode2020;
using System.Diagnostics;
using System.Threading.Tasks;
using System.Collections.Concurrent;

namespace HashCode2021
{
    public static class InputReader
    {
        public static Model Read(string file)
        {
            var lines = File.ReadAllLines($"../../../input/{file}");

            // ================ CUSTOM INPUT READ START =========================
            var (b, l, days) = lines[0].Split3();

            var scores = lines[1].Split(" ");

            var books = new Dictionary<int, Book>();

            for (int i = 0; i < b; i++)
            {
                books.Add(i, new Book(i, int.Parse(scores[i])));
            }

            var libraries = new ConcurrentDictionary<int, Library>();

            Parallel.For(0, l, i =>
            {
                var (nBooks, signup, capacity) = lines[2+i*2].Split3();

                var booksL = lines[2 + i * 2 + 1].Split(" ").Select(id => books[int.Parse(id)]).ToHashSet();

                libraries.TryAdd(i, new Library(i, capacity, signup, booksL));
            });


            // Log info about the model
            L.Log($"days: {days}, books: {books.Count}, libraries: {libraries.Count}");



            return new Model(books, new Dictionary<int, Library>(libraries), days);
            // ================ CUSTOM INPUT READ END =========================
        }
    }
}

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
            var (duration, P, S, C, bonus) = lines[0].Split5();


            var places = new ConcurrentDictionary<int, Place>();
            var streets = new ConcurrentDictionary<int, Street>();

            Parallel.For(0, S, i =>
            {
                var (orig, dest, name, cost) = lines[i+1].Split4<int,int,string,int>();

                var originPlace = new Place(orig);
                var destPlace = new Place(dest);

                streets.TryAdd(i, new Street(name, cost, orig, dest));
            });

            var scores = lines[1].Split(" ");

            var books = new Dictionary<int, Car>();

            for (int i = 0; i < b; i++)
            {
                books.Add(i, new Car(i, int.Parse(scores[i])));
            }


            


            // Log info about the model
            L.Log($"days: {days}, books: {books.Count}, libraries: {libraries.Count}");



            return new Model(books, new Dictionary<int, Street>(libraries), days);
            // ================ CUSTOM INPUT READ END =========================
        }
    }
}

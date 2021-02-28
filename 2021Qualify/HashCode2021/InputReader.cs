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


            var places = new Dictionary<int, Place>();
            var streets = new Dictionary<string, Street>();

            for(int i = 0; i < S; i++)
            {
                var (orig, dest, name, cost) = lines[i + 1].Split4<int,int,string,int>();

                if (!places.ContainsKey(orig))
                {
                    places.Add(orig, new Place(orig));
                }

                if (!places.ContainsKey(dest))
                {
                    places.Add(dest, new Place(dest));
                }
                var originPlace = places[orig];
                var destPlace = places[dest];

                var street = new Street(name, cost, orig, dest);
                streets.Add(name, street);

                destPlace.inDestinations.Add(orig, originPlace);
                destPlace.inStreets.Add(street);

                originPlace.outDestinations.Add(dest, destPlace);
                originPlace.outStreets.Add(name, street);
            }

            var cars = new Dictionary<int, Car>();
            for (int i = 0; i < C; i++)
            {
                var split = lines[i + 1 + S].Split(" ");
                var car = new Car(i);
                var score = 0;
                foreach (var street in split.Skip(1))
                {
                    car.AddToRoute(streets[street]);
                    score += streets[street].cost;
                }
                car.score = ((double)(duration - score) / (double)duration) / 1f;
                if (car.score < 0) car.score = 0;
                cars.Add(i, car);
            }

            // Log info about the model
            L.Log($"duration: {duration}, intersections: {P}, streets: {S}, cars: {C}, bonus: {bonus}");



            return new Model(cars, places, duration, bonus, streets);
            // ================ CUSTOM INPUT READ END =========================
        }
    }
}

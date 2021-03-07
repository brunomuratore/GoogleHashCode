using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using static HashCode2021.Solver;

namespace HashCode2021
{
    internal class Rating
    {
        private Dictionary<string, (int score, bool best)> ratings = new Dictionary<string, (int, bool)>();
        private string PATH = "../../../ratings.txt";

        public Rating(HashSet<string> files)
        {
            if (!File.Exists(PATH))
            {
                File.WriteAllLines(PATH, files.Select(f => $"{f} -1").ToArray());
            }

            var lines = File.ReadAllLines(PATH);

            foreach(var line in lines)
            {
                var (file, score) = line.Split2<string,int>(" ");
                ratings[file] = (score, false);
            }
        }

        internal int Calculate(string file, Result r)
        {
            // ================ CUSTOM SCORE CALCULATION START =========================
            // Just fill score variable

            r.places.Values.ToList().ForEach(c => c.Reset(false));
            r.streets.Values.ToList().ForEach(c => c.Reset());
            r.cars.ToList().ForEach(c => c.Reset());

            var score = 0;

            foreach (var p in r.places.Values)
            {
                p.CalculateSchedule();
            }

            for (int t = 0; t <= r.duration; t++)
            {
                foreach (var car in r.cars)
                {
                    if (t < car.timeAtPlace) continue;

                    if (car.route.Count == 1)
                    {
                        // car arrived, add score
                        score += r.bonus;
                        score += r.duration - t;
                        car.timeAtPlace = r.duration + 1;
                        continue;
                    }

                    var street = car.route.First();
                    if (!car.waiting)
                    {
                        car.waiting = true;
                        street.carsAtPlace.Enqueue(car);
                    }

                    // verify if this car should move
                    var place = r.places[street.dest];
                    var openStreet = place.OpenStreet(t);
                    if (openStreet?.id != street.id || street.carsAtPlace.Peek().id != car.id || street.lastCarHere == t) continue;

                    // move to next place  
                    car.route.RemoveFirst();
                    street.carsAtPlace.Dequeue();
                    street.lastCarHere = t;
                    var nextStreet = car.route.First.Value;
                    car.timeAtPlace = t + nextStreet.cost;
                    car.waiting = false;
                }
            }

            // ================ CUSTOM SCORE CALCULATION END =========================

            Print(file, score);
            Save(file, score);
            return score;
        }

        private void Print(string file, int score)
        {
            char symbol = '=';
            var color = ConsoleColor.DarkGray;
            if (score > ratings[file].score) { symbol = '+'; color = ConsoleColor.Green; }
            else if (score < ratings[file].score) { symbol = '-'; color = ConsoleColor.Red; }

            var msg = $"{file}: {score} ([{symbol}{Math.Abs(score - ratings[file].score)}])";
            Utils.AddSummary(msg, color);
            Utils.AddTotal(score);
        }
                
        private void Save(string file, int score) 
        {
            if (score > ratings[file].score)
            {
                ratings[file] = (score, true);
                File.WriteAllLines(PATH, ratings.Select(r => $"{r.Key} {r.Value.score}").ToArray());
            }            
        }

        public HashSet<string> GetNewBest()
        {
            return ratings.Where(r => r.Value.best).Select(r => r.Key).ToHashSet();
        }
    }
}
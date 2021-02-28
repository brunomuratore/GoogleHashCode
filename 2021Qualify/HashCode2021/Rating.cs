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
            
            var score = 0;

            foreach (var car in r.cars) car.waiting = false;

            for (int t = 0; t < r.seconds; t++)
            {
                foreach (var car in r.cars)
                {
                    if (car.timeAtPlace > t || car.finalRoute.Count == 1) continue;

                    var street = car.finalRoute.First();
                    if (!car.waiting)
                    {
                        car.waiting = true;
                        street.carsAtPlace.Enqueue(car);
                    }

                    // Set new schedule for street if not previously set
                    var place = r.places[street.dest];
                    var streetLight = place.schedules[street.id];
                    if (streetLight.order == int.MaxValue)
                    {
                        var totalLights = place.schedules.Count;
                        var bestOrder = t % totalLights;
                        while (place.isSet(bestOrder))
                        {
                            bestOrder += 1;
                            if (bestOrder >= totalLights) bestOrder = 0;
                        }
                        streetLight.order = bestOrder;
                        place.CalculateScheduleD();
                    }
                                        
                    // verify if this car should move
                    var openStreet = place.OpenStreet(t);
                    if (openStreet?.id != street.id || (car.finalRoute.Count > 1 && street.carsAtPlace.Peek().id != car.id)) continue;

                    car.finalRoute.RemoveFirst();

                    if (car.finalRoute.Count > 1)
                    {
                        street.carsAtPlace.Dequeue();
                        // move to next place             
                        var nextStreet = car.finalRoute.First.Value;
                        car.timeAtPlace = t + nextStreet.cost;

                        car.waiting = false;
                    }
                    else
                    {
                        // car at last route, see if can finish and add score
                        var lastRoute = car.finalRoute.First.Value;

                        if (lastRoute.cost + t < r.seconds)
                        {
                            score += r.bonus;
                            score += r.seconds - t - lastRoute.cost;
                        }
                    }
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
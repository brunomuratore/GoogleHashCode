using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
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
                                    
            for(int t = 0; t < r.seconds; t++)
            {
                foreach(var place in r.places.Where(p => p.schedules.Count > 0))
                {
                    // find if there is a car at this place on the street with open semaphore at this time
                    var openStreet = place.OpenStreet(t);
                    var carsAtPlace = openStreet.carsAtPlace.Where(c => t >= c.timeAtPlace).ToList();
                    if (carsAtPlace.Count == 0) continue;

                    // move car
                    var carToMove = carsAtPlace.First();
                    carToMove.route.RemoveFirst();
                    openStreet.carsAtPlace.Remove(carToMove);

                    if (carToMove.route.Count > 1)
                    {
                        // move to next place
                        var nextStreet = carToMove.route.First.Value;
                        carToMove.timeAtPlace += nextStreet.cost;
                        nextStreet.carsAtPlace.Add(carToMove);
                    } 
                    else
                    {
                        // car at last route, see if can finish and add score
                        var lastRoute = carToMove.route.First.Value;

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
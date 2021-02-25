using HashCode2020;
using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace HashCode2021
{
    internal partial class Solver
    {
        private readonly string file;

        public Solver(string file)
        {
            this.file = file;
        }

        public Result Solve(Model m)
        {
            return file switch
            {
                "a.txt" => SolveGeneric(m),
                _ => SolveGeneric(m),
            };
        }

        public Result SolveGeneric(Model m)
        {
            // ================ CUSTOM SOLVER START =========================            
            //var bestLibraries = m.libraries.Values.OrderByDescending(l => l.maxPotentialScore).ToList();
            //var resultLibraries = new List<Street>();

            foreach (var car in m.cars)
            {
                foreach(var street in car.Value.route)
                {
                    street.countCarsPassingBy += 1;
                }
            }

            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets.Values;
                var totalCarsPassingByIntersec = streets.Select(s => s.countCarsPassingBy).Sum();
                foreach(var street in streets)
                {
                    var time = (float)street.countCarsPassingBy / totalCarsPassingByIntersec;
                    if (time < 1) { time *= 10; }
                    place.schedules.Add(new Schedule(street, (int)time));
                }
            }
            
            return new Result(m.places.Values.ToList(), m.duration, m.bonus, m.cars.Values.ToList());
            // ================ CUSTOM SOLVER END =========================
        }

        // Use in case of expensive loops
        //var timer = new Stopwatch();
        //timer.Start();
        //while(condition && timer.ElapsedMilliseconds< 1000 * 5)
        //{

        //}
    }
}
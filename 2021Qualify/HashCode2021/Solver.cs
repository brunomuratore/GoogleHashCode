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
                    street.countCarsPassingBy += car.Value.score;
                    street.countScore += car.Value.score;
                }
            }

            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets.Values;
                var totalCarsPassingByIntersec = streets.Select(s => s.countScore).Sum();
                if (totalCarsPassingByIntersec == 0) continue;

                var times = new List<int>(); 

                foreach (var street in streets)
                {
                    times.Add( (int) ((float)street.countScore / totalCarsPassingByIntersec * 10F));
                }

                foreach (var street in streets)
                {
                    if (street.countScore == 0) continue;

                    var time = (int)((float)street.countScore / totalCarsPassingByIntersec * 10F);
                    if (time == 10) time = 1;
                    else
                    {
                        var lcm = LCM(times);
                        if (lcm == time) time = 1;
                        else
                        {
                            time = (int)Math.Round(time / 3f);
                            if (time < 1) time = 1;
                        }                            
                    }
                    place.schedules.Add(new Schedule(street, time));
                }

            }
            
            return new Result(m.places.Values.ToList(), m.duration, m.bonus, m.cars.Values.ToList());
            // ================ CUSTOM SOLVER END =========================
        }


        public static int LCM (List<int> list)
        {
            if (list.Count == 0) return 0;
            if (list.Count == 1) return 1;
            if (list.Count == 2) return LCM(list[0], list[1]);

            return LCM(list[0], LCM(list.Skip(1).ToList()));
        }
        public static int LCM(int a, List<int> list)
        {
            if (list.Count == 1) return LCM(list[0], list[1]);

            return LCM(a, LCM(list.Skip(1).ToList()));
        }

        public static int LCM(int a, int b) //method for finding LCM with parameters a and b
        {
            int num1, num2;                         //taking input from user by using num1 and num2 variables
            if (a > b)
            {
                num1 = a; num2 = b;
            }
            else
            {
                num1 = b; num2 = a;
            }

            for (int i = 1; i <= num2; i++)
            {
                if ((num1 * i) % num2 == 0)
                {
                    return i * num1;
                }
            }
            return num2;
        }
        // Use in case of expensive loops
        //var timer = new Stopwatch();
        //timer.Start();
        //while(condition && timer.ElapsedMilliseconds< 1000 * 5)
        //{

        //}
    }
}
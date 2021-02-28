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
                "a.txt" => SolveD(m),
                _ => SolveD(m),
            };
        }

        private Result SolveD(Model m)
        {
            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets.Values;
                place.totalCarsPassingByIntersec = streets.Select(s => s.countCarsPassingBy).Sum();
                if (place.totalCarsPassingByIntersec == 0) continue;

                foreach (var street in streets)
                {
                    if (street.countCarsPassingBy == 0) continue;

                    var time = 1;
                    place.schedules.Add(new Schedule(street, time));
                }
                place.CalculateSchedule();
            }

            for(int t = 0; t < m.duration; t++)
            {
                //if (placesWithDefaultSchedule.Count == 0) break;

                foreach(var place in m.places.Values.Where(p => p.totalCarsPassingByIntersec > 0))
                {
                    var idx = 0;
                    foreach(var street in place.inStreets.Values.Where(s => s.carsAtPlace.Where(c => t >= c.timeAtPlace).Count() > 0).OrderBy(s => s.carsAtPlace.Where(c => t >= c.timeAtPlace).Count()))
                    {
                        // Verify if any car is waiting at this light
                        var carsAtPlace = street.carsAtPlace.Where(c => t >= c.timeAtPlace).ToList();
                        if (carsAtPlace.Count == 0) continue;

                        // Set new schedule for street if not previously set
                        var totalLights = place.schedules.Count;
                        var streetLight = place.schedules.Single(s => s.street.id == street.id);
                        if (streetLight.order == -1)
                        {
                            var bestOrder = t % totalLights;
                            if(place.isSet(bestOrder))
                            {
                                bestOrder += 1;
                                if (bestOrder >= totalLights) bestOrder = 0;
                            }
                            streetLight.order = bestOrder;
                            place.CalculateSchedule();
                        }

                        // Run simulation by moving all cars at this second
                        var openStreet = place.OpenStreet(t);
                        if (openStreet.id != street.id) continue;

                        var carToMove = carsAtPlace.First();
                        carToMove.route.RemoveFirst();
                        openStreet.carsAtPlace.Remove(carToMove);

                        if (carToMove.route.Count > 1)
                        {
                            // move to next place
                            var nextStreet = carToMove.route.First.Value;
                            carToMove.timeAtPlace = t + nextStreet.cost;
                            nextStreet.carsAtPlace.Add(carToMove);
                        }
                    }
                }
            }


            return new Result(m.places.Values.ToList(), m.duration, m.bonus, m.cars.Values.ToList(), m.streets);
        }

        public Result SolveGeneric(Model m)
        {
            // ================ CUSTOM SOLVER START =========================            
            
            HashSet<string> streetsWhereCarsStart = new HashSet<string>();

            foreach (var car in m.cars)
            {
                streetsWhereCarsStart.Add(car.Value.route.First.Value.id);
            }

            var rnd = new Random();
            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets.Values;
                var totalScore = streets.Select(s => s.countScore).Sum();
                if (totalScore == 0) continue;

                var times = new List<int>(); 

                foreach (var street in streets)
                {
                    times.Add( (int) ((float)street.countScore / totalScore * 10F));
                }
                                
                foreach (var street in streets)
                {
                    if (street.countScore == 0) continue;

                    var time = (int)((float)street.countScore / totalScore * 10F);
                    if (time == 10) time = 1;
                    else
                    {
                        var lcm = LCM(times);
                        if (lcm == time) time = 1;
                        else
                        {
                            time = (int)Math.Round((time * 1f));
                            if (time < 1) time = 1;
                        }
                    }

                    place.schedules.Add(new Schedule(street, time));
                }

                place.schedules = place.schedules.OrderByDescending(s => streetsWhereCarsStart.Contains(s.street.id) ? 1 : 0).ToList();
                place.CalculateSchedule();
                //if (place.schedules.Count > 5) place.schedules.RemoveAt(place.schedules.Count - 1);
                //var rest = place.schedules.Where(s => s.street.id != top.street.id).OrderByDescending(s => s.time).ToList();

                //place.schedules = new List<Schedule> { top }.Concat(rest).ToList();
            }
            
            return new Result(m.places.Values.ToList(), m.duration, m.bonus, m.cars.Values.ToList(), m.streets);
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
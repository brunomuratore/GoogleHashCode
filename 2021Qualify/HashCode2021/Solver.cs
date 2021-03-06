using HashCode2020;
using HashCode2020.models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

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
                "e.txt" => SolveGeneric(m),
                "f.txt" => SolveGeneric(m),
                _ => SolveD(m),
            };
        }

        private Result SolveD(Model m)
        {
            var rnd = new Random();
            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets;
                place.totalCarsPassingByIntersec = streets.Select(s => s.countCarsPassingBy).Sum();
                if (place.totalCarsPassingByIntersec == 0) continue;

                foreach (var street in streets)
                {
                    if (street.countCarsPassingBy == 0) continue;

                    var time = 1;
                    place.schedules.Add(street.id, new Schedule(street, time));
                }
            }


            for (int i = 0; i < 2; i++) // Run a second time removing schedules that are never used
            {
                m.cars.Values.ToList().ForEach(c => c.Reset());
                m.places.Values.ToList().ForEach(c => c.Reset());
                m.streets.Values.ToList().ForEach(c => c.Reset());

                for (int t = 0; t <= m.duration; t++)
                {
                    foreach (var car in m.cars.Values)
                    {
                        if (car.timeAtPlace > t || car.route.Count == 1) continue;

                        var street = car.route.First();

                        if (!car.waiting) // Enqueue this car only one time
                        {
                            car.waiting = true;
                            street.carsAtPlace.Enqueue(car);
                        }

                        // Set new schedule for street if not previously set
                        var place = m.places[street.dest];
                        var schedule = place.schedules[street.id];
                        if (schedule.order == int.MaxValue) // Schedule not set to a order yet
                        {
                            var totalSchedules = place.schedules.Count;
                            var bestOrder = t % totalSchedules;
                            while (place.isSet(bestOrder))
                            {
                                bestOrder++;
                                if (bestOrder >= totalSchedules) bestOrder = 0;
                            }
                            schedule.order = bestOrder;
                            place.CalculateScheduleD();
                        }

                        // verify if this car should move
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

                foreach (var p in m.places.Values.Where(p => p.schedules.Values.Any(s => s.order == int.MaxValue)))
                {
                    p.schedulesToRemove = p.schedules.Values.Where(s => s.order == int.MaxValue).Select(s => s.street.id).ToHashSet();
                }
            }

            

            return new Result(m.places, m.duration, m.bonus, m.cars.Values.ToList(), m.streets);
        }

        public Result SolveGeneric(Model m)
        {
            // ================ CUSTOM SOLVER START =========================            
            
            HashSet<string> streetsWhereCarsStart = new HashSet<string>();

            foreach (var car in m.cars.Values)
            {
                streetsWhereCarsStart.Add(car.route.First.Value.id);
                foreach(var route in car.route)
                {
                    route.carsScore += car.score;
                }
            }

            var rnd = new Random();
            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets;
                var totalScore = streets.Select(s => s.carsScore).Sum();
                if (totalScore == 0) continue;

                var times = new List<int>(); 

                foreach (var street in streets)
                {
                    times.Add( (int) ((float)street.carsScore / totalScore * 10F));
                }
                                
                foreach (var street in streets)
                {
                    if (street.carsScore == 0) continue;

                    var time = (int)((float)street.carsScore / totalScore * 10F);
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

                    place.schedules.Add(street.id, new Schedule(street, time));
                }

                place.schedules = place.schedules.OrderByDescending(s => streetsWhereCarsStart.Contains(s.Value.street.id) ? 1 : 0).ToDictionary(x => x.Key, x => x.Value);
                place.CalculateSchedule();
                //if (place.schedules.Count > 5) place.schedules.RemoveAt(place.schedules.Count - 1);
                //var rest = place.schedules.Where(s => s.street.id != top.street.id).OrderByDescending(s => s.time).ToList();

                //place.schedules = new List<Schedule> { top }.Concat(rest).ToList();
            }
            
            return new Result(m.places, m.duration, m.bonus, m.cars.Values.ToList(), m.streets);
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
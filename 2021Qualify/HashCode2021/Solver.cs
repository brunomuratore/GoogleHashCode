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
        private object tLock = new object();
        private object cLock = new object();

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


            for (int i = 0; i < 2; i++) 
            {
                m.places.Values.ToList().ForEach(c => c.Reset());
                m.streets.Values.ToList().ForEach(c => c.Reset());
                m.cars.Values.ToList().ForEach(c => c.Reset());

                for (int t = 0; t <= m.duration; t++)
                {

                    //+7k random on D
                    //Parallel.ForEach(m.cars.Values, car =>
                    foreach (var car in m.cars.Values)
                    {
                        if (car.timeAtPlace > t || car.route.Count == 1) continue;

                        var street = car.route.First();

                        if (!car.waiting) // Enqueue this car only one time
                        {
                            car.waiting = true;
                            lock (cLock)
                            {
                                street.carsAtPlace.Enqueue(car);
                            }
                        }

                        // Set new schedule for street if not previously set
                        var place = m.places[street.dest];
                        var schedule = place.schedules[street.id];
                        lock (tLock)
                        {
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
                        }

                        // verify if this car should move
                        var openStreet = place.OpenStreet(t);
                        lock (cLock)
                        {
                            if (openStreet?.id != street.id || street.carsAtPlace.Peek().id != car.id || street.lastCarHere == t) continue;

                            // move to next place     
                            street.carsAtPlace.Dequeue();
                            street.lastCarHere = t;
                        }
                        car.route.RemoveFirst();
                        var nextStreet = car.route.First.Value;
                        car.timeAtPlace = t + nextStreet.cost;
                        car.waiting = false;
                    }
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
                foreach(var route in car.route.SkipLast(1))
                {
                    route.carsScore += car.score;
                }
            }

            var rnd = new Random();
            foreach (var place in m.places.Values)
            {
                var streets = place.inStreets;
                var totalScore = streets.Select(s => s.countCarsPassingBy).Sum();
                if (totalScore == 0) continue;

                var times = new List<int>(); 

                foreach (var street in streets.Where(s => s.countCarsPassingBy > 0))
                {
                    times.Add( (int) ((float)street.countCarsPassingBy / totalScore * 10F));
                }
                                
                foreach (var street in streets.Where(s => s.countCarsPassingBy > 0))
                {

                    var time = (int)((float)street.countCarsPassingBy / totalScore * 10F);
                    if (time == 10) time = 1;
                    else
                    {
                        var lcm = LCM(times);
                        if (times.All(x => x == time)) time = 1;
                        else
                        {
                            var multiplier = file == "e.txt" ? 0.2f : 1f;
                            if (times.Count(x => x == 0) > times.Count / 1.3 && times.Count(x => x != 0) == 1 && time > 3) multiplier = 1.5f;
                            time = (int)Math.Round(time * multiplier);
                            if (time < 1) time = 1;
                        }
                    }

                    place.schedules.Add(street.id, new Schedule(street, time));
                }

                place.schedules = place.schedules.OrderByDescending(s => streetsWhereCarsStart.Contains(s.Value.street.id) ? 1 : 0).ToDictionary(x => x.Key, x => x.Value);
                                
                var top = new List<Schedule>() { place.schedules.First().Value };
                var rest = place.schedules.Values.Where(s => s.street.id != top.First().street.id).OrderBy(s => s.street.countCarsPassingBy * (file == "e.txt" ? -1 : 1)).ToList();

                place.schedules = top.Concat(rest).ToDictionary(k => k.street.id, v => v);
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
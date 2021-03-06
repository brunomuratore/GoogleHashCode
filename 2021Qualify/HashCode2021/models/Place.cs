using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace HashCode2020.models
{
    public class Place
    {
        public int id;
        public List<Street> inStreets = new List<Street>();        
        public Dictionary<string, Street> outStreets = new Dictionary<string, Street>();
        public Dictionary<int, Place> inDestinations = new Dictionary<int, Place>();
        public Dictionary<int, Place> outDestinations = new Dictionary<int, Place>();
        public Dictionary<string, Schedule> schedules = new Dictionary<string, Schedule>();

        private int rotation = 0;
        private Dictionary<int, Street> timers = new Dictionary<int, Street>();

        //used by score
        public int curScheduleIndex = 0; //current green light
        public double totalCarsPassingByIntersec = 0;
        internal HashSet<string> schedulesToRemove = new HashSet<string>();

        public Place(int id)
        {
            this.id = id;
        }

        internal Street OpenStreet(int t)
        {
            return timers.GetValueOrDefault(t % rotation);
        }

        internal void CalculateSchedule()
        {
            timers.Clear();
            rotation = schedules.Values.Sum(x => x.time);
            var t = 0;
            foreach(var s in schedules.Values.OrderBy(s => s.order))
            {
                for (int i = 0; i < s.time; i++)
                {
                    timers.Add(i + t, s.street);
                }

                t+= s.time;
            }
        }

        internal void CalculateScheduleD()
        {
            timers.Clear();
            rotation = schedules.Values.Sum(x => x.time);
            foreach (var s in schedules.Values.Where(s => s.order != int.MaxValue).OrderBy(s => s.order))
            {                
                timers.Add(s.order, s.street);          
            }
        }

        internal bool isSet(int module)
        {
            return timers.ContainsKey(module);
        }

        internal void Reset(bool resetScore = true)
        {
            timers.Clear();
            if(resetScore)
            {
                schedules = schedules.Where(s => !schedulesToRemove.Contains(s.Value.street.id)).ToDictionary(k => k.Key, v => v.Value);
                foreach (var s in schedules)
                {
                    s.Value.order = int.MaxValue;
                }
            }
        }
    }
}

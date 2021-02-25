using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Place
    {
        public int id;
        public Dictionary<string, Street> inStreets = new Dictionary<string, Street>();
        public Dictionary<string, Street> outStreets = new Dictionary<string, Street>();
        public Dictionary<int, Place> inDestinations = new Dictionary<int, Place>();
        public Dictionary<int, Place> outDestinations = new Dictionary<int, Place>();
        public List<Schedule> schedules = new List<Schedule>();

        public Place(int id)
        {
            this.id = id;
        }
    }
}

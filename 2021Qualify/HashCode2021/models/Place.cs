using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Place
    {
        public int id;
        public Dictionary<int, Street> inStreets = new Dictionary<int, Street>();
        public Dictionary<int, Street> outStreets = new Dictionary<int, Street>();
        public Dictionary<int, Place> inDestinations = new Dictionary<int, Place>();
        public Dictionary<int, Place> outDestinations = new Dictionary<int, Place>();
        public List<Schedule> schedules = new List<Schedule>();

        public Place(int id)
        {
            this.id = id;
        }
    }
}

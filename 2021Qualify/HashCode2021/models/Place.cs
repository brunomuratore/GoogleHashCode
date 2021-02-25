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


        public Place(int id, Dictionary<int, Street> inStreets, Dictionary<int, Street> outStreets, Dictionary<int, Place> inDestinations, Dictionary<int, Place> outDestinations)
        {
            this.id = id;
        }
    }
}

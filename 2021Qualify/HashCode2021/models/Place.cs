using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Place
    {
        public int id;
        public Dictionary<int, Street> inStreets;
        public Dictionary<int, Street> outStreets;
        public Dictionary<int, Place> inDestinations;
        public Dictionary<int, Place> outDestinations;

        public Place(int id, Dictionary<int, Street> inStreets, Dictionary<int, Street> outStreets, Dictionary<int, Place> inDestinations, Dictionary<int, Place> outDestinations)
        {
            this.inStreets = inStreets;
            this.outStreets = outStreets;
            this.inDestinations = inDestinations;
            this.outDestinations = outDestinations;
            this.id = id;
        }
    }
}

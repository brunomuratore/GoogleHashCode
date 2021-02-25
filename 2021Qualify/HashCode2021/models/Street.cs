using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HashCode2020.models
{
    public class Street
    {
        public string id;
        public int cost;
        public Place origin;
        public Place dest;

        public Street(string id, int cost, Place origin, Place dest)
        {
            this.id = id;
            this.cost = cost;
            this.origin = origin;
            this.dest = dest;
        }
                
    }
}

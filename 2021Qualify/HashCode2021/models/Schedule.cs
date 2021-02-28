using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HashCode2020.models
{
    public class Schedule
    {
        public Street street;
        public int time; // seconds
        public int order = -1;

        public Schedule(Street street, int time)
        {
            this.street = street;
            this.time = time;
        }
                
    }
}

using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Car
    {
        public int id;
        public List<Street> route = new List<Street>();
        public int score = 0;

        // used by score
        public int curstreetindex = 0; // index of current street where the car is in its route

        public Car(int id)
        {            
            this.id = id;
        }
    }
}

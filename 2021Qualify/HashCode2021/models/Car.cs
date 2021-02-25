using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Car
    {
        public int id;
        public int score;

        public List<Street> route;

        public Car(int id, int score, List<Street> route)
        {
            this.score = score;
            this.id = id;
            this.route = route;
        }
    }
}

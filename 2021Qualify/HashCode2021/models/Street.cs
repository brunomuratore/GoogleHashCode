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
        public int origin;
        public int dest;
        public double countCarsPassingBy = 0;
        public double carsScore = 0;
        public Queue<Car> carsAtPlace = new Queue<Car>();
        internal int lastCarHere = -1;

        public Street(string id, int cost, int origin, int dest)
        {
            this.id = id;
            this.cost = cost;
            this.origin = origin;
            this.dest = dest;
        }

        public void Reset()
        {
            lastCarHere = -1;
            carsAtPlace.Clear();
        }
                
    }
}

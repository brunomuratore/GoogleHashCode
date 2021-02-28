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
        public double countScore = 0;
        public List<Car> carsAtPlace = new List<Car>();
        public List<Car> carsArrivingAtPlace = new List<Car>();

        public Street(string id, int cost, int origin, int dest)
        {
            this.id = id;
            this.cost = cost;
            this.origin = origin;
            this.dest = dest;
        }
                
    }
}

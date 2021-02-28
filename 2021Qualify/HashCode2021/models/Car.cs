using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Car
    {
        public int id;
        public LinkedList<Street> route = new LinkedList<Street>();
        public LinkedList<Street> finalRoute = new LinkedList<Street>();
        public double score = 0; // calculated at input

        public int timeAtPlace = 0;
        internal bool waiting = false;

        public Car(int id)
        {            
            this.id = id;
        }

        internal void AddToRoute(Street street)
        {
            // if (route.Count == 0) street.carsAtPlace.Enqueue(this);

            street.countCarsPassingBy += 1;
            street.countScore += score;
            route.AddLast(street);
            finalRoute.AddLast(street);
        }
    }
}

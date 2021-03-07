using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Car
    {
        public int id;
        public LinkedList<Street> originalRoute = new LinkedList<Street>();
        public LinkedList<Street> route = new LinkedList<Street>();
        public double score = 0; // calculated at input

        public int timeAtPlace = 0;
        internal bool waiting = false;

        public Car(int id)
        {            
            this.id = id;
        }

        internal void AddToRoute(Street street, bool lastRoute)
        {
            if (!lastRoute) street.countCarsPassingBy += 1;
            route.AddLast(street);
            originalRoute.AddLast(street);
        }

        public void Reset()
        {
            timeAtPlace = 0;
            waiting = true;
            route = new LinkedList<Street>(originalRoute);
            route.First.Value.carsAtPlace.Enqueue(this);
        }
    }
}

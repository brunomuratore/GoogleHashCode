using System.Collections.Generic;
using HashCode2020.models;

namespace HashCode2021
{
    public class Model
    {
        public Dictionary<int, Car> cars;
        public Dictionary<int, Place> places;
        public int duration;
        public int bonus;
                

        public Model(Dictionary<int, Car> cars, Dictionary<int, Street> places, int duration, int bonus)
        {
            this.cars = cars;
            this.places = places;
            this.duration = duration;
            this.bonus = bonus;
        }
    }
}

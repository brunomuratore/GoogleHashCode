using System.Collections.Generic;
using HashCode2020.models;

namespace HashCode2021
{
    public class Model
    {
        public Dictionary<int, Car> cars;
        public Dictionary<int, Place> places;
        public Dictionary<string, Street> streets;
        public int duration;
        public int bonus;


        public Model(Dictionary<int, Car> cars, Dictionary<int, Place> places, int duration, int bonus, Dictionary<string, Street> streets)
        {
            this.cars = cars;
            this.places = places;
            this.duration = duration;
            this.bonus = bonus;
            this.streets = streets;
        }
    }
}

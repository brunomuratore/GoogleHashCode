using System.Collections.Generic;
using HashCode2020.models;

namespace HashCode2021
{
    public class Model
    {
        public Dictionary<int, Car> cars;
        public Dictionary<int, Street> streets;
        public int duration;
        public int bonus;
                

        public Model(Dictionary<int, Car> cars, Dictionary<int, Street> streets, int duration, int bonus)
        {
            this.cars = cars;
            this.streets = streets;
            this.duration = duration;
            this.bonus = bonus;
        }
    }
}

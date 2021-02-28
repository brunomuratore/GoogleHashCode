using HashCode2020.models;
using System.Collections.Generic;

namespace HashCode2021
{
    internal partial class Solver
    {
        public class Result
        {
            public int bonus; //bonus for each car that reaches destination
            public List<Place> places; // places where schedules have been set
            public Dictionary<string, Street> streets; // places where schedules have been set
            public int seconds; //total seconds available in simulation
            public List<Car> cars; //cars with their routes

            public Result(List<Place> places, int seconds, int bonus, List<Car> cars, Dictionary<string, Street> streets)
            {
                this.streets = streets;
                this.places = places;
                this.seconds = seconds;
                this.cars = cars;
                this.bonus = bonus;
            }
        }
    }
}
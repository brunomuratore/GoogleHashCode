using HashCode2020.models;
using System.Collections.Generic;

namespace HashCode2021
{
    internal partial class Solver
    {
        public class Result
        {
            public List<Place> places;


            public Result(List<Street> l, List<Place> places)
            {
                this.libraries = l;
                this.places = days;
            }
        }
    }
}
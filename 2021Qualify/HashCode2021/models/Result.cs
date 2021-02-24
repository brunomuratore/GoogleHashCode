using HashCode2020.models;
using System.Collections.Generic;

namespace HashCode2021
{
    internal partial class Solver
    {
        public class Result
        {
            public List<Library> libraries;
            public int days;

            public Result(List<Library> l, int days)
            {
                this.libraries = l;
                this.days = days;
            }
        }
    }
}
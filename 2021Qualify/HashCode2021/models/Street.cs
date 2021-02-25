using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HashCode2020.models
{
    public class Street
    {
        public int id;
        public HashSet<Car> books;
        public int capacity;
        public int signup;
        public List<Car> scan = new List<Car>();

        public int maxPotentialScore = 0;

        public Street(int id, int capacity, int signup, HashSet<Car> booksL)
        {
            this.id = id;
            this.capacity = capacity;
            this.signup = signup;
            this.books = booksL;
        }

        internal void Calculate()
        {
            // if need to recalculate score
        }
                
    }
}

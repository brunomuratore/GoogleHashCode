using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Car
    {
        public int id;
        public List<Street> route = new List<Street>();

        public Car(int id)
        {            
            this.id = id;
        }
    }
}

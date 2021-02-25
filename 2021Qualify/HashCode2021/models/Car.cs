using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Car
    {
        public int id;
        public int score;

        public Car(int id, int score)
        {
            this.score = score;
            this.id = id;
        }
    }
}

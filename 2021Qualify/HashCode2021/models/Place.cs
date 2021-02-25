using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Place
    {
        public int id;
        public int score;

        public Place(int id, int score)
        {
            this.score = score;
            this.id = id;
        }
    }
}

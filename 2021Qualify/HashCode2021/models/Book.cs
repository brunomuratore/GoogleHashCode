using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Book
    {
        public int id;
        public int score;

        public Book(int id, int score)
        {
            this.score = score;
            this.id = id;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Text;

namespace HashCode2020.models
{
    public class Library
    {
        public int id;
        public HashSet<Book> books;
        public int capacity;
        public int signup;
    }
}

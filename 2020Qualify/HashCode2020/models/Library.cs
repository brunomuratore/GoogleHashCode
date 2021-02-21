using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HashCode2020.models
{
    public class Library
    {
        public int id;

        public Library(int id, int capacity, int signup, HashSet<Book> booksL)
        {
            this.id = id;
            this.capacity = capacity;
            this.signup = signup;
            this.books = booksL;
            scan = books.ToList();
        }

        public HashSet<Book> books;
        public int capacity;
        public int signup;
        public List<Book> scan;
    }
}

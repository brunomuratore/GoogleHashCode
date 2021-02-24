using System.Collections.Generic;
using HashCode2020.models;

namespace HashCode2021
{
    public class Model
    {
        public Dictionary<int, Book> books;
        public Dictionary<int, Library> libraries;
        public int days;

        public Model(Dictionary<int, Book> books, Dictionary<int, Library> libraries, int days)
        {
            this.books = books;
            this.libraries = libraries;
            this.days = days;
        }
    }
}

using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Linq;
using HashCode2020.models;

namespace HashCode2021
{
    public static class InputReader
    {
        public static Model Read(string file)
        {
            var lines = File.ReadAllLines($"../../../input/{file}");

            var (B, L, days) = lines[0].Split3();

            var scores = lines[1].Split(" ");

            var books = new Dictionary<int, Book>();

            for (int i = 0; i < B; i++)
            {
                books.Add(i, new Book(i, int.Parse(scores[i])));
            }

            var libraries = new Dictionary<int, Library>();

            for (int i = 2; i < L * 2; i += 2)
            {
                var (nBooks, signup, capacity) = lines[i].Split3();

                var booksL = lines[i + 1].Split(" ").Select(id => books[int.Parse(id)]).ToHashSet();


                libraries.Add(i, new Library(i, capacity, signup, booksL));
            }

            return new Model(books, libraries, days);
        }
    }

    public struct Model
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

        public override bool Equals(object obj)
        {
            return obj is Model other &&
                   EqualityComparer<Dictionary<int, Book>>.Default.Equals(books, other.books) &&
                   EqualityComparer<Dictionary<int, Library>>.Default.Equals(libraries, other.libraries) &&
                   days == other.days;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(books, libraries, days);
        }

        public void Deconstruct(out Dictionary<int, Book> books, out Dictionary<int, Library> libraries, out int days)
        {
            books = this.books;
            libraries = this.libraries;
            days = this.days;
        }

        public static implicit operator (Dictionary<int, Book> books, Dictionary<int, Library> libraries, int days)(Model value)
        {
            return (value.books, value.libraries, value.days);
        }

        public static implicit operator Model((Dictionary<int, Book> books, Dictionary<int, Library> libraries, int days) value)
        {
            return new Model(value.books, value.libraries, value.days);
        }
    }
}

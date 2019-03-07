package com.mfeia.book.server_automaiton;

import java.util.Map;

public interface BooksMap {
    void addBooks(Book books);

    Map<Long, Book> getBooksListMap();

     void booksMapCirculation(BooksMapCirculationCallBack booksMapCirculationCallBack);

}

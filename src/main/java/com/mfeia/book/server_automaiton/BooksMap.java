package com.mfeia.book.server_automaiton;

import java.util.Map;

public interface BooksMap {
   void addBooks(Books books);
   Map<Long, Books> getBooksListMap();
}

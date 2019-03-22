package server_automaiton_gather.server_automaiton_interface;

import com.mfeia.book.server_automaiton.Book;

import java.util.Map;

public interface BooksMap {
    void addBook(Book book);

    Map<Long, Book> getBooksListMap();

    void booksMapCirculation(BooksMapCirculationCallBack booksMapCirculationCallBack);

}

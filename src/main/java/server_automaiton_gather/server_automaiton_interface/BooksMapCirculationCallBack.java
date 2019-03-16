package server_automaiton_gather.server_automaiton_interface;

import com.mfeia.book.server_automaiton.Book;

public interface BooksMapCirculationCallBack {
    void bookCirculation(Book book, double circulationNumber) throws Exception;
}

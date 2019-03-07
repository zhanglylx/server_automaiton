package com.mfeia.book.server_automaiton;

public interface BooksMapCirculationCallBack {
    void bookCirculation(Book book,double circulationNumber) throws Exception;
}

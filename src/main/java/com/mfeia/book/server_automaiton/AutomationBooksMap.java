package com.mfeia.book.server_automaiton;

import java.util.*;

public class AutomationBooksMap implements BooksMap {
    private Map<Long, Book> booksList;
    private static AutomationBooksMap automationBooksMap = new AutomationBooksMap();

    private AutomationBooksMap() {
        this.booksList = Collections.synchronizedMap(new HashMap<>());
        this.booksList.put(811400194l,
                new Book(811400194, "侯府长媳", "淮西", "#f29fa9")
        );
    }

    public static AutomationBooksMap getAutomationBooksMap() {
        return automationBooksMap;
    }

    @Override
    public void addBooks(Book books) {
        this.booksList.put(books.getBookId(), books);

    }

    @Override
    public Map<Long, Book> getBooksListMap() {
        return this.booksList;
    }
}

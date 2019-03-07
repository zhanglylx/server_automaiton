package com.mfeia.book.server_automaiton;

import ZLYUtils.DoubleOperation;

import java.io.*;
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
        //保存book到本地
//        if (!this.booksList.containsKey(books.getBookId())) {
//            try {
//                OutputStream outputStream = new FileOutputStream(new File("search.txt"), true);
//                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//                PrintWriter printWriter = new PrintWriter(outputStreamWriter, true);
//                printWriter.println(books.getBookName());
//                OutputStream outputStreamId = new FileOutputStream(new File("searchid.txt"), true);
//                OutputStreamWriter outputStreamWriterId = new OutputStreamWriter(outputStreamId, "UTF-8");
//                PrintWriter printWriterId = new PrintWriter(outputStreamWriterId, true);
//                printWriterId.println(books.getBookId());
//                printWriter.close();
//                printWriterId.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        this.booksList.put(books.getBookId(), books);
    }


    @Override
    public Map<Long, Book> getBooksListMap() {
        return this.booksList;
    }

    @Override
    public void booksMapCirculation(BooksMapCirculationCallBack booksMapCirculationCallBack) {
        int index = String.valueOf(getBooksListMap().size()).length();
        double initial = DoubleOperation.div(1, Math.pow(10, index));
        double forInitial = initial;

        for (Iterator<Map.Entry<Long, Book>> iterator =
             getBooksListMap().entrySet().iterator()
             ; iterator.hasNext(); ) {
            final double number = forInitial;
            final Book book = iterator.next().getValue();
            try {
                AutomationUtils.addExecute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            booksMapCirculationCallBack.bookCirculation(book,
                                    number);
                        } catch (Exception e) {
                            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(AutomationBooksMap.class,
                                    "booksMapCirculation_bookCirculation异常", e)
                            );
                        }
                    }
                });
            } catch (Exception e) {
                RealizePerform.getRealizePerform().addtestFrameList(new ErrException(AutomationBooksMap.class,
                        "booksMapCirculation_addExecute异常", e)
                );
            }


            forInitial = DoubleOperation.add(forInitial, initial);
        }
    }


}

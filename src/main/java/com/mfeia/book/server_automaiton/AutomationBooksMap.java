package com.mfeia.book.server_automaiton;

import ZLYUtils.DoubleOperation;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.BooksMap;
import server_automaiton_gather.server_automaiton_interface.BooksMapCirculationCallBack;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.util.*;

public class AutomationBooksMap implements BooksMap {
    private Map<Long, Book> booksList;
    private static AutomationBooksMap automationBooksMap = new AutomationBooksMap();

    private AutomationBooksMap() {
        this.booksList = Collections.synchronizedMap(new HashMap<>());
        try {
            this.booksList.put(811400194l,
                    new Book(Long.parseLong(AutomationUtils.getServerAutomaitonProperties("inlayBookId")),
                            AutomationUtils.getServerAutomaitonProperties("inlayBookName"),
                            AutomationUtils.getServerAutomaitonProperties("inlayBookAuthon"),
                            AutomationUtils.getServerAutomaitonProperties("inlayBookCategoryColor")));
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(AutomationBooksMap.class, "inlayBook", e));
        }

    }

    public static AutomationBooksMap getAutomationBooksMap() {
        return automationBooksMap;
    }

    @Override
    public void addBook(Book book) {
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
        try {
            int bookMap = Integer.parseInt(AutomationUtils.getServerAutomaitonProperties("bookMap"));
            if (this.booksList.size() >= bookMap && bookMap > 0)
                return;
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(AutomationBooksMap.class, "addBook", e));
        }
        this.booksList.put(book.getBookId(), book);
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

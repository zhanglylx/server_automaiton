package com.mfeia.book.server_automaiton.book_content;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;
import server_automaiton_gather.ErrException;

import java.util.*;

public class TestCasesBookContent implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws InterruptedException {
        JSONObject jsonObject = JSONObject.fromObject(AutoHttpUtils.doGet(
                BookContentConfig.BOOK_CONTENT_SUB_SIDY_MESSAGE, "", number + 0.1
        ));
        performInspection.addtestFrameList(
                new SubSidyMessage(jsonObject), number + 0.1
        );
        Book books;
        number = DoubleOperation.add(number, 0.20000);
        for (Iterator<Map.Entry<Long, Book>> iterator =
             AutomationBooksMap.getAutomationBooksMap().getBooksListMap().entrySet().iterator()
             ; iterator.hasNext(); ) {
            number = DoubleOperation.add(number, 0.00001);
            books = iterator.next().getValue();
            try {
                jsonObject = JSONObject.fromObject(AutoHttpUtils.doGet(
                        BookContentConfig.BOOK_CONTEN_CHAPTER_READ, "full=0&chapterId=0&bookId=" + books.getBookId()
                        , number));
                performInspection.addtestFrameList(
                        new ChapterRead(jsonObject, books, number), number);
                final double numberRecommend = number;
                final Book bookRecommend = books;
                AutomationUtils.addExecute(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = JSONObject.fromObject(AutoHttpUtils.doPost(
                                BookContentConfig.BOOK_CONTEN_RECOMMEND, AutoHttpUtils.getPostMap(bookRecommend)
                                , numberRecommend));
                        performInspection.addtestFrameList(
                                new Recommend(jsonObject, bookRecommend, numberRecommend), numberRecommend
                        );

                    }
                });


            } catch (Exception e) {
                performInspection.addtestFrameList(
                        new ErrException(TestCasesBookContent.class, books.getBookId(), e, number), number
                );
            }
        }
    }

}

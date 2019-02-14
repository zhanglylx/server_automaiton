package com.mfeia.book.server_automaiton.book_content;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import com.mfeia.book.server_automaiton.detail_page.TestCasesDetail;
import net.sf.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class TestCasesBookContent implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws InterruptedException {
        JSONObject jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                AutomationUtils.BOOK_CONTENT_SUB_SIDY_MESSAGE, ""
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
                jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                        AutomationUtils.BOOK_CONTEN_CHAPTER_READ, "full=0&chapterId=0&bookId=" + books.getBookId()
                ));
                performInspection.addtestFrameList(
                        new ChapterRead(jsonObject, books, number), number);
                jsonObject = JSONObject.fromObject(AutomationUtils.doPost(
                        AutomationUtils.BOOK_CATALOG_RECOMMEND, AutomationUtils.getPostMap(books)
                ));
                performInspection.addtestFrameList(
                        new Recommend(jsonObject, books, number), number
                );

            } catch (Exception e) {
                performInspection.addtestFrameList(
                        new ErrException(TestCasesDetail.class, books.getBookId(), e, number), number
                );
            }
        }

    }
}

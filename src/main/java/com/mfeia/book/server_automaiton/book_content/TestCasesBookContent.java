package com.mfeia.book.server_automaiton.book_content;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import net.sf.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class TestCasesBookContent implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws InterruptedException {
        JSONObject jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                "cx/intShop/subSidyMessage", ""
        ));
        performInspection.addtestFrameList(
                new SubSidyMessage(jsonObject), number + 0.1
        );
        Books books;
        number = DoubleOperation.add(number, 0.20000);
        for (Iterator<Map.Entry<Long, Books>> iterator =
             AutomationBooksMap.getAutomationBooksMap().getBooksListMap().entrySet().iterator()
             ; iterator.hasNext(); ) {
            number = DoubleOperation.add(number, 0.00001);
            books = iterator.next().getValue();
            jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                    "cx/itf/chapterRead", "full=0&chapterId=0&bookId=" + books.getBookId()
            ));
            performInspection.addtestFrameList(
                    new ChapterRead(jsonObject, books, number), number);
        }


    }
}

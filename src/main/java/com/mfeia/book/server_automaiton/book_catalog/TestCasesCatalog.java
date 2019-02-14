package com.mfeia.book.server_automaiton.book_catalog;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import com.mfeia.book.server_automaiton.detail_page.Detail;
import com.mfeia.book.server_automaiton.detail_page.TestCasesDetail;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 检查目录中id不重复
 * 检查目录刷新
 */
public class TestCasesCatalog implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws InterruptedException {
        JSONObject jsonObject;
        for (Map.Entry<Long, Book> booksEntry :
                AutomationBooksMap.getAutomationBooksMap().getBooksListMap().entrySet()) {
            number = DoubleOperation.add(number, 0.00001);
            try {
                jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                        AutomationUtils.BOOK_CATALOG_GETVOLUME, "bookId=" + booksEntry.getKey()
                ));
                performInspection.addtestFrameList(
                        new Catalog(jsonObject, jsonObject.getJSONArray("list"), number,
                                booksEntry.getValue()),
                        number
                );
                jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                        AutomationUtils.BOOK_CATALOG_IS_CHAOTER_UPDATE, "updatetime=0&bookId=" + booksEntry.getKey()
                ));
                performInspection.addtestFrameList(
                        new IsChapterUpdate(jsonObject, booksEntry.getValue(), number), number
                );

            } catch (Exception e) {
                performInspection.addtestFrameList(
                        new ErrException(TestCasesDetail.class, booksEntry.getKey(), e, number), number
                );
            }
        }
    }
}

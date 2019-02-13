package com.mfeia.book.server_automaiton.book_catalog;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import com.mfeia.book.server_automaiton.detail_page.Detail;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 检查目录中id不重复
 * 检查目录刷新
 */
public class TestCasesCatalog implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws InterruptedException {
        JSONObject jsonObject;
        for (Map.Entry<Long, Books> booksEntry :
                AutomationBooksMap.getAutomationBooksMap().getBooksListMap().entrySet()) {
            number = DoubleOperation.add(number, 0.00001);
            jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                    "cx/itf/getvolume", "bookId=" + booksEntry.getKey()
            ));
            performInspection.addtestFrameList(
                    new Catalog(jsonObject, jsonObject.getJSONArray("list"), number,
                            booksEntry.getValue()),
                    number
            );
            jsonObject = JSONObject.fromObject(AutomationUtils.doGet(
                    "cx/itf/isChapterUpdate", "updatetime=0&bookId=" + booksEntry.getKey()
            ));
            performInspection.addtestFrameList(
                    new IsChapterUpdate(jsonObject, booksEntry.getValue(),number), number
            );
        }
    }
}

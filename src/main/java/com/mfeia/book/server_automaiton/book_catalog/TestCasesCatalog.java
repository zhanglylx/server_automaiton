package com.mfeia.book.server_automaiton.book_catalog;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import com.mfeia.book.server_automaiton.detail_page.TestCasesDetail;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;
import server_automaiton_gather.ErrException;

import java.util.Map;

/**
 * 检查目录中id不重复
 * 检查目录刷新
 */
public class TestCasesCatalog implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws InterruptedException {
        JSONObject jsonObject;
        for (Map.Entry<String, Book> booksEntry :
                AutomationBooksMap.getAutomationBooksMap().getBooksListMap().entrySet()) {
            number = DoubleOperation.add(number, 0.00001);
            try {
                jsonObject = JSONObject.fromObject(AutoHttpUtils.doGet(
                        BookCatalogConfig.BOOK_CATALOG_GETVOLUME, "bookId=" + booksEntry.getKey()
                        , number,Catalog.class
                ));
                performInspection.addtestFrameList(
                        new Catalog(jsonObject, jsonObject.getJSONArray("list"), number,
                                booksEntry.getValue()),
                        number
                );
                jsonObject = JSONObject.fromObject(AutoHttpUtils.doGet(
                        BookCatalogConfig.BOOK_CATALOG_IS_CHAOTER_UPDATE, "updatetime=0&bookId=" + booksEntry.getKey()
                        , number,IsChapterUpdate.class));
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

package com.mfeia.book.server_automaiton.detail_page;


import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 实现详情页的作者其他图书，大家都在看和详情页的基本数据检查
 * 未实现:评论，写书评
 */
public class TestCasesDetail implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number)  {
           for(Map.Entry<Long, Books> booksEntry :
                   AutomationBooksMap.getAutomationBooksMap().getBooksListMap().entrySet()) {
               number = DoubleOperation.add(number, 0.00001);
               performInspection.addtestFrameList(
                       new Detail(JSONObject.fromObject(AutomationUtils.doGet(
                               "cx/bookDetailYS", "bookid=" + booksEntry.getKey()
                       )), number, booksEntry.getValue()),
                       number
               );
           }

    }
}

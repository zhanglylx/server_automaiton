package com.mfeia.book.server_automaiton.jenkins_build;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import com.mfeia.book.server_automaiton.book_content.TestCasesBookContent;
import net.sf.json.JSONObject;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestCasesJenkinsBuild implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
        AutomationBooksMap.getAutomationBooksMap().booksMapCirculation(new BooksMapCirculationCallBack() {
            @Override
            public void bookCirculation(Book book, double circulationNumber) {
                try {
                    circulationNumber = DoubleOperation.add(circulationNumber, number);
                    String cnid = AutomationUtils.getServerAutomaitonProperties(
                            AutomationUtils.CNID);
                    String s = AutomationUtils.doGet(
                            AutomationUtils.JENKINS_PACKAGE_BOOK_INFO,
                            "bookId=" + book.getBookId() + "&cnid=" +
                                    cnid
                    );
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    PackageBookInfo packageBookInfo = new PackageBookInfo(jsonObject, book);
                    performInspection.addtestFrameList(packageBookInfo, circulationNumber
                    );
                    String host = AutomationUtils.getServerAutomaitonProperties(
                            AutomationUtils.JENKINS_GET_CHAPTER_HOST
                    );
                    String path = AutomationUtils.getServerAutomaitonProperties(
                            AutomationUtils.JENKINS_GET_CHAPTER
                    );
                    jsonObject = JSONObject.fromObject(
                            AutomationUtils.doGet(host, path, "cnid=" + cnid +
                                    "&bookid=" + packageBookInfo.getBook().getBookId() +
                                    "&chapterid=" + packageBookInfo.getmFirstChapter())
                    );
                    performInspection.addtestFrameList(new Getchapter(jsonObject), circulationNumber);

                } catch (Exception e) {
                    performInspection.addtestFrameList(
                            new ErrException(TestCasesBookContent.class, book.getBookId(), e, circulationNumber), circulationNumber
                    );
                }


            }
        });
    }
}

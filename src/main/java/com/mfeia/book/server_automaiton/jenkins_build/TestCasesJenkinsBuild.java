package com.mfeia.book.server_automaiton.jenkins_build;

import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.*;
import com.mfeia.book.server_automaiton.book_content.TestCasesBookContent;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.BooksMapCirculationCallBack;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.ErrException;

public class TestCasesJenkinsBuild implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws Exception {
        AutomationBooksMap.getAutomationBooksMap().booksMapCirculation(new BooksMapCirculationCallBack() {
            @Override
            public void bookCirculation(Book book, double circulationNumber) {
                try {
                    circulationNumber = DoubleOperation.add(circulationNumber, number);
                    String cnid = AutomationUtils.getServerAutomaitonProperties(
                            AutomationUtils.CNID);
                    String s = AutoHttpUtils.doGet(
                            JenkinsBuildConfig.JENKINS_PACKAGE_BOOK_INFO,
                            "bookId=" + book.getBookId() + "&cnid=" +
                                    cnid
                    );
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    PackageBookInfo packageBookInfo = new PackageBookInfo(jsonObject, book);
                    performInspection.addtestFrameList(packageBookInfo, circulationNumber
                    );
                    String host = AutomationUtils.getServerAutomaitonProperties(
                            JenkinsBuildConfig.JENKINS_GET_CHAPTER_HOST
                    );
                    String path = AutomationUtils.getServerAutomaitonProperties(
                            JenkinsBuildConfig.JENKINS_GET_CHAPTER
                    );
                    String quers = "cnid=" + cnid +
                            "&bookid=" + packageBookInfo.getBook().getBookId() +
                            "&chapterid=" + packageBookInfo.getmFirstChapter();
                    jsonObject = JSONObject.fromObject(
                            AutoHttpUtils.doGet(host, path, quers));
                    performInspection.addtestFrameList(new Getchapter(jsonObject).setCaseName(quers), circulationNumber);

                } catch (Exception e) {
                    performInspection.addtestFrameList(
                            new ErrException(TestCasesBookContent.class, book, e, circulationNumber), circulationNumber
                    );
                }


            }
        });
    }
}

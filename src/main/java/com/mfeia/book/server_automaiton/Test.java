package com.mfeia.book.server_automaiton;

import com.mfeia.book.server_automaiton.book_catalog.TestCasesCatalog;
import com.mfeia.book.server_automaiton.book_content.TestCasesBookContent;
import com.mfeia.book.server_automaiton.boutique.TestCasesBoutique;
import com.mfeia.book.server_automaiton.detail_page.TestCasesDetail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 精品页检查
 */
public class Test {
    public static final int BOUTIQUE_TAG = 1;//精品代码

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        new RunnableAddTestCasess(new TestCasesBoutique(), BOUTIQUE_TAG).run();
        executorService.shutdown();
//        while (true) {
//            if (executorService.isTerminated()) {
//                System.out.println(RealizePerform.getRealizePerform().toString());
//                break;
//            }
//        }
//        new RunnableAddTestCasess(new TestCasesDetail(), 2).run();
//        new RunnableAddTestCasess(new TestCasesCatalog(), 3).run();
        new RunnableAddTestCasess(new TestCasesBookContent(), 4).run();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println(RealizePerform.getRealizePerform().toString());
                break;
            }
        }
    }

    static class RunnableAddTestCasess implements Runnable {

        private AddTestCases addTestCases;
        private double tag;

        public RunnableAddTestCasess(AddTestCases addTestCases, double tabNumber) {
            this.addTestCases = addTestCases;
            this.tag = tabNumber;
        }

        public void run() {
            addTestCases(this.addTestCases);
        }

        private void addTestCases(AddTestCases addTestCase) {
            String className = "NULL";
            try {
                if (addTestCase == null) throw new NullPointerException();
                className = addTestCase.getClass().getName();
                addTestCase.additionTestCases(RealizePerform.getRealizePerform(), this.tag);
            } catch (Exception e) {
                RealizePerform.getRealizePerform().addtestFrameList(new ErrException(this.getClass(),
                        className + ":addTestCasesException", e, this.tag
                ), this.tag);
            }

        }

        public void setAddTestCases(AddTestCases addTestCases) {
            this.addTestCases = addTestCases;
        }

        public void setTag(double tag) {
            this.tag = tag;
        }

    }


}

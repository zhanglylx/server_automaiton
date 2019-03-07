package com.mfeia.book.server_automaiton;

import com.mfeia.book.server_automaiton.background_interface.TestCasesBackgroundInterface;
import com.mfeia.book.server_automaiton.book_catalog.TestCasesCatalog;
import com.mfeia.book.server_automaiton.book_content.TestCasesBookContent;
import com.mfeia.book.server_automaiton.boutique.TestCasesBoutique;
import com.mfeia.book.server_automaiton.detail_page.TestCasesDetail;
import com.mfeia.book.server_automaiton.jenkins_build.TestCasesJenkinsBuild;
import com.mfeia.book.server_automaiton.make_money.TestCasesMakeMoney;
import com.mfeia.book.server_automaiton.user_related.TestCasesUserRelated;

import java.io.IOException;

/**
 * 精品页检查
 */
public class Test {
    public static final int BOUTIQUE_TAG = 1;//精品代码

    public static void main(String[] args) throws IOException {
        try {
            new RunnableAddTestCasess(new TestCasesBoutique(), BOUTIQUE_TAG).run();
            System.out.println(RealizePerform.getRealizePerform().toString());
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesDetail(), 2));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesCatalog(), 3));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBookContent(), 4));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesJenkinsBuild(), 5));


            AutomationUtils.addExecute(new RunnableAddTestCasess(
                    new TestCasesBackgroundInterface(), 10
            ));
            AutomationUtils.addExecute(new RunnableAddTestCasess(
                    new TestCasesUserRelated(), 9
            ));
//            DBUtils.getConnection();
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesMakeMoney(), 11));
            while (true) {
                if (AutomationUtils.getExecutorServiceActiveCount() == 0) {
                    AutomationUtils.executorServiceShutdown();
                }
                if (AutomationUtils.executorServiceisTerminated()) {
                    System.out.println(RealizePerform.getRealizePerform().toString());
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AutomationUtils.executorServiceShutdown();
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

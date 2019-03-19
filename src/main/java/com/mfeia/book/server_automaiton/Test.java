package com.mfeia.book.server_automaiton;

import com.mfeia.book.server_automaiton.background_interface.TestCasesBackgroundInterface;
import com.mfeia.book.server_automaiton.book_catalog.TestCasesCatalog;
import com.mfeia.book.server_automaiton.book_content.TestCasesBookContent;
import com.mfeia.book.server_automaiton.boutique.TestCasesBoutique;
import com.mfeia.book.server_automaiton.detail_page.TestCasesDetail;
import com.mfeia.book.server_automaiton.integral_record.TestCasesIntegralRecord;
import com.mfeia.book.server_automaiton.jenkins_build.TestCasesJenkinsBuild;
import com.mfeia.book.server_automaiton.make_money.TestCasesMakeMoney;
import com.mfeia.book.server_automaiton.paiHang_stackRoom.TestCasesPaiHangStackRoom;
import com.mfeia.book.server_automaiton.search.TestCasesSearch;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_Utils.DBUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import com.mfeia.book.server_automaiton.shelf_related.TestCasesShelfRelated;
import com.mfeia.book.server_automaiton.start_related.TestCasesStartRelated;
import com.mfeia.book.server_automaiton.user_related.TestCasesUserRelated;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 精品页检查
 */
public class Test {
    private static final int BOUTIQUE_TAG = 1;//精品代码

    public static void main(String[] args) throws IOException {
        try {
//           new RunnableAddTestCasess(new TestCasesBoutique(), BOUTIQUE_TAG).run();
//           AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesPaiHangStackRoom(),15));
//           AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesSearch(),20));
//            while (true) {
//                if (AutomationUtils.getExecutorServiceActiveCount() == 0) {
//                    System.out.println(RealizePerform.getRealizePerform().toString());
//                    break;
//                }
//                Thread.sleep(1000);
//            }
//            System.out.println(AutomationBooksMap.getAutomationBooksMap().getBooksListMap().size());
//
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesDetail(), 2));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesCatalog(), 3));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBookContent(), 4));
////            //AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesJenkinsBuild(), 5));
//
//
//            AutomationUtils.addExecute(new RunnableAddTestCasess(
//                    new TestCasesBackgroundInterface(), 10
//            ));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(
//                    new TestCasesUserRelated(), 9
//            ));
//
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesMakeMoney(), 11));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesIntegralRecord(), 12));
           AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesStartRelated(),13));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesShelfRelated(), 14));
//////


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

        RunnableAddTestCasess(AddTestCases addTestCases, double tabNumber) {
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
                addTestCase.additionTestCases( RealizePerform.getRealizePerform(), this.tag);
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

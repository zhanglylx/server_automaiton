package com.mfeia.book.server_automaiton;

import ZLYUtils.JavaUtils;
import ZLYUtils.WindosUtils;
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
import server_automaiton_gather.server_automaiton_Utils.*;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import com.mfeia.book.server_automaiton.shelf_related.TestCasesShelfRelated;
import com.mfeia.book.server_automaiton.start_related.TestCasesStartRelated;
import com.mfeia.book.server_automaiton.user_related.TestCasesUserRelated;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.*;
import java.util.*;

/**
 * 精品页检查
 */
public class Test {
    private static final int BOUTIQUE_TAG = 1;//精品代码

    private static final boolean IS_JAR_RUN = false;


    /**
     * 用于判断是否需要打包运行，如果要是运行，获取config文件时会选择指定的路径
     *
     * @return
     */
    public static boolean isJarRun() {
        return IS_JAR_RUN;
    }

    public static void main(String[] args) {
        try {
            /*
            精品页，排行，搜索会将bookMap填充，所以需要先单独运行
             */
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBoutique(), BOUTIQUE_TAG));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesPaiHangStackRoom(), 15));
            waitThread();
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesSearch(), 20));
            waitThread();
            System.out.println("获取到的书籍数量:" +
                    AutomationBooksMap.getAutomationBooksMap().getBooksListMap().size());

            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesDetail(), 2));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesCatalog(), 3));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBookContent(), 4));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesJenkinsBuild(), 5));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBackgroundInterface(), 10));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesUserRelated(), 9));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesMakeMoney(), 11));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesIntegralRecord(), 12));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesStartRelated(), 13));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesShelfRelated(), 14));
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(Test.class, "run", e));
        }
        File[] files = new File[]{};
        String html = "";
        try {

            while (true) {
                if (AutomationUtils.getExecutorServiceActiveCount() == 0) {
                    AutomationUtils.executorServiceShutdown();
                }
                if (AutomationUtils.executorServiceisTerminated()) {
                    break;
                }
                Thread.sleep(1000);
            }
            boolean saveCaseToHtml = HtmlUtils.saveCaseToHtml(RealizePerform.getRealizePerform(), AutomationUtils.getCaseStartTime());
            boolean logUtils = LogUtils.logHtmlFormatting();
            html = HtmlUtils.getHtmlHeaders("server_automaiton");
            html += "<b>";
            html += HtmlUtils.getColourFormatting("尊敬的各位领导，同事们:", "#FF00FF");
            html += "</br>";
            html += HtmlUtils.getColourFormatting(HtmlUtils.getSeparator(4) + "大家好!", "663399");
            html += "</br>";
            html += HtmlUtils.getColourFormatting(HtmlUtils.getSeparator(4) + "今天，由松鼠接口智能检测系统为大家汇报" + HtmlUtils.getColourFormatting("【免费电子书】", HtmlUtils.getRandomColour()) + "执行结果", "663399");
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "执行汇总结果保存 :  "
                    + (saveCaseToHtml ? HtmlUtils.getColourFormatting("成功", "339900") : HtmlUtils.getColourFormatting("失败", "CC0000"));
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "错误日志结果保存 : "
                    + (logUtils ? HtmlUtils.getColourFormatting("成功", "339900") : HtmlUtils.getColourFormatting("失败", "CC0000"));
            html += "</br>";
            String bookMapSize = (HtmlUtils.getColourFormatting(
                    String.valueOf(AutomationBooksMap
                            .getAutomationBooksMap()
                            .getBooksListMap()
                            .size()), HtmlUtils.getRandomColour()));

            html += HtmlUtils.getSeparator(4) + "共获取到检查书籍 : "
                    + JavaUtils.strFormatting(bookMapSize, AutomationBooksMap.getBookMapSstrictInfo());
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "用例成功执行条数 : "
                    + HtmlUtils.getColourFormatting(String.valueOf(RealizePerform.getRealizePerform().getSucceedBranches()), "339900");
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "用例失败执行条数 : "
                    + HtmlUtils.getColourFormatting(String.valueOf(RealizePerform.getRealizePerform().getFailureBranches()), "CC0000");
            html += "</br>";
            html += "</br>";
            html += "</br>";
            html += "</br>";
            html += "</br>";
            html += HtmlUtils.getSeparator(100)
                    + HtmlUtils.getColourFormatting(HtmlUtils.getMotivationalRandom(), HtmlUtils.getRandomColour());
            html += "</b>";
            if (saveCaseToHtml) {
                files = Arrays.copyOf(files, files.length + 1);
                files[files.length - 1] = HtmlUtils.getExecutiveOutcomeslogFile();
            }
            if (logUtils) {
                files = Arrays.copyOf(files, files.length + 1);
                files[files.length - 1] = LogUtils.getSavelLogFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(Test.class, "stop", e));
        } finally {
            AutomationUtils.executorServiceShutdown();
//            if (RealizePerform.getRealizePerform().getFailureBranches() > 0)
            MailUtils.sendMail(files, WindosUtils.getDate() + " 自动化脚本执行结果", html);
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

    private static void waitThread() throws InterruptedException {
        while (AutomationUtils.getExecutorServiceActiveCount() != 0) {
            Thread.sleep(1000);
        }
    }

}

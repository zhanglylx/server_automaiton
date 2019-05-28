package com.mfeia.book.server_automaiton;

import ZLYUtils.JavaUtils;
import ZLYUtils.WindosUtils;
import ZLYUtils.ZipUtils;
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
            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesBoutique(), BOUTIQUE_TAG));
            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesPaiHangStackRoom(), 15));
//            ThreadPoolUtils.waitThread();
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesSearch(), 20));
//            ThreadPoolUtils.waitThread();
//            System.out.println("获取到的书籍数量:" +
//                    AutomationBooksMap.getAutomationBooksMap().getBooksListMap().size());
//
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesDetail(), 2));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesCatalog(), 3));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesBookContent(), 4));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesJenkinsBuild(), 5));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesBackgroundInterface(), 10));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesUserRelated(), 9));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesMakeMoney(), 11));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesIntegralRecord(), 12));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesStartRelated(), 13));
//            ThreadPoolUtils.addExecute(new RunnableAddTestCasessUtils(new TestCasesShelfRelated(), 14));
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(Test.class, "run", e));
        }
        List<File> filesList = new ArrayList<>();
        String html = "";
        String errTitle = "";
        try {

            while (true) {
                if (ThreadPoolUtils.getExecutorServiceActiveCount() == 0) {
                    ThreadPoolUtils.executorServiceShutdown();
                }
                if (ThreadPoolUtils.executorServiceisTerminated()) {
                    break;
                }
                Thread.sleep(1000);
            }
//            boolean saveCaseToHtml = HtmlUtils.saveCaseToHtml(RealizePerform.getRealizePerform(), AutomationUtils.getCaseStartTime());
            boolean logUtils = LogUtils.logHtmlFormatting();
            html = HtmlUtils.getHtmlHeaders("server_automaiton");
            html += "<b>";
            html += HtmlUtils.getColourFormatting("尊敬的各位领导，同事们:", "#FF00FF");
            html += "</br>";
            html += HtmlUtils.getColourFormatting(HtmlUtils.getSeparator(4) + "大家好!", "663399");
            html += "</br>";
            html += HtmlUtils.getColourFormatting(HtmlUtils.getSeparator(4) + "今天，由松鼠接口智能检测系统为大家汇报" + HtmlUtils.getColourFormatting("【免费电子书】", HtmlUtils.getRandomColour()) + "执行结果", "663399");
            html += "</br>";
//            html += HtmlUtils.getSeparator(4) + "执行汇总结果保存 :  "
//                    + (saveCaseToHtml ? HtmlUtils.getColourFormatting("成功", "339900") : HtmlUtils.getColourFormatting("失败", "CC0000"));
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
//            if (saveCaseToHtml) {
//                filesList.add(HtmlUtils.getExecutiveOutcomeslogFile());
//            }
            if (logUtils) filesList.add(LogUtils.getSavelLogFile());
            File zip = new File("serverAutomaiton.zip");
            ZipUtils.toZip(filesList, zip);
            filesList.clear();
            filesList.add(zip);
        } catch (Exception e) {
            e.printStackTrace();
            errTitle = JavaUtils.strFormatting("出现了未知错误", e);
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(Test.class, "stop", e));
        } finally {
            ThreadPoolUtils.executorServiceShutdown();
//            if (RealizePerform.getRealizePerform().getFailureBranches() > 0)
            MailUtils.sendMail(filesList, WindosUtils.getDate() + " 自动化脚本执行结果" + errTitle, html);
        }
    }
}
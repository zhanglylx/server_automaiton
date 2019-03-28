package com.mfeia.book.server_automaiton;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * 精品页检查
 */
public class Test {
    private static final int BOUTIQUE_TAG = 1;//精品代码
    private static List<String> motivational;
    private static final boolean IS_JAR_RUN = false;

    static {
        motivational = new ArrayList<>();
        motivational.add("只要你相信，奇迹一定会实现。");
        motivational.add("智者的梦再美，也不如愚人实干的脚印。");
        motivational.add("儿童有无抱负，这无关紧要，可成年人则不可胸无大志。");
        motivational.add("沧海可填山可移，男儿志气当如斯。");
        motivational.add("只要精神不滑坡，办法总比困难多。");
        motivational.add("立志是事业的大门，工作是登门入室的旅程。");
        motivational.add("燕雀安知鸿鹄之志哉。");
        motivational.add("石看纹理山看脉，人看志气树看材。");
        motivational.add("鸟不展翅膀难高飞。");
        motivational.add("只要能收获甜蜜，荆棘丛中也会有蜜蜂忙碌的身影。");
        motivational.add("人生的成败往往就在一念之间。");
        motivational.add("顶天立地奇男子，要把乾坤扭转来。");
        motivational.add("器大者声必闳，志高者意必远。");
        motivational.add("才自清明志自高。");
        motivational.add("进取用汗水谱烈军属着奋斗和希望之歌。");
        motivational.add("志坚者，功名之柱也。登山不以艰险而止，则必臻乎峻岭。");
        motivational.add("骄傲是断了引线的风筝稍纵即逝。");
        motivational.add("人无志向，和迷途的盲人一样。");
        motivational.add("胸有凌云志，无高不可攀。");
        motivational.add("胸无大志，枉活一世。");
        motivational.add("与其当一辈子乌鸦，莫如当一次鹰。");
        motivational.add("大丈夫处世，不能立功建业，几与草木同腐乎？");
        motivational.add("如果圆规的两只脚都动，永远也画不出一个圆。");
        motivational.add("古之立大事者，不惟有超世之材，亦必有坚忍不拨之志。");

    }

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
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBoutique(), BOUTIQUE_TAG));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesPaiHangStackRoom(), 15));
//            waitThread();
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesSearch(), 20));
//            waitThread();
//            System.out.println("获取到的书籍数量:"+
//                    AutomationBooksMap.getAutomationBooksMap().getBooksListMap().size());
//
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesDetail(), 2));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesCatalog(), 3));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBookContent(), 4));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesJenkinsBuild(), 5));
            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesBackgroundInterface(), 10));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesUserRelated(), 9));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesMakeMoney(), 11));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesIntegralRecord(), 12));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesStartRelated(), 13));
//            AutomationUtils.addExecute(new RunnableAddTestCasess(new TestCasesShelfRelated(), 14));
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
            html = "<!DOCTYPE html>";
            html += "<html>";
            html += "<head>";
            html += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
            html += "<title>server_automaiton</title>";
            html += "</head>";
            html += "<b>";
            html += HtmlUtils.colourFormatting("尊敬的各位领导，同事们:", "#FF00FF");
            html += "</br>";
            html += HtmlUtils.colourFormatting(HtmlUtils.getSeparator(4) + "大家好!", "663399");
            html += "</br>";
            html += HtmlUtils.colourFormatting(HtmlUtils.getSeparator(4) + "今天，由松鼠接口智能检测系统为大家汇报" + HtmlUtils.colourFormatting("【免费电子书】", HtmlUtils.getRandomColour()) + "执行结果", "663399");
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "执行汇总结果保存 :  "
                    + (saveCaseToHtml ? HtmlUtils.colourFormatting("成功", "339900") : HtmlUtils.colourFormatting("失败", "CC0000"));
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "错误日志结果保存 : "
                    + (logUtils ? HtmlUtils.colourFormatting("成功", "339900") : HtmlUtils.colourFormatting("失败", "CC0000"));
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "用例成功执行条数 : "
                    + HtmlUtils.colourFormatting(String.valueOf(RealizePerform.getRealizePerform().getSucceedBranches()), "339900");
            html += "</br>";
            html += HtmlUtils.getSeparator(4) + "用例失败执行条数 : "
                    + HtmlUtils.colourFormatting(String.valueOf(RealizePerform.getRealizePerform().getFailureBranches()), "CC0000");
            html += "</br>";
            html += "</br>";
            html += "</br>";
            html += "</br>";
            html += "</br>";
            html += HtmlUtils.getSeparator(100)
                    + HtmlUtils.colourFormatting(motivational.get(new Random().nextInt(motivational.size())), HtmlUtils.getRandomColour());
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
            if(RealizePerform.getRealizePerform().getFailureBranches()>0)MailUtils.sendMail(files, WindosUtils.getDate() + " 自动化脚本执行结果", html);
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

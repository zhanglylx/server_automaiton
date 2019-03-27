package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.WindosUtils;
import org.apache.commons.lang.StringUtils;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogUtils {
    private static PrintWriter printWriter;
    private static File savelLog;
    private static final String LOG_TITLE =
            "<strong>用例启动时间 : " + AutomationUtils.getCaseStartTime() + "</strong>";

    public static File getSavelLogFile() {
        return savelLog;
    }

    static {
        try {
            String errorSaveAddress = AutomationUtils.getServerAutomaitonProperties("error.message.save.address").trim();
            errorSaveAddress = errorSaveAddress.replace("\\", File.separator);
            errorSaveAddress = errorSaveAddress.replace("/", File.separator);
            savelLog = new File(errorSaveAddress);
            File fileParent = savelLog.getParentFile();
            if (!fileParent.exists()) fileParent.mkdirs();
            printWriter = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(savelLog, false), "UTF-8"),
                    true);
            printWriter.println(LOG_TITLE);
            printWriter.println("<!DOCTYPE html>");
            printWriter.println("<html>");
            printWriter.println("<head>");
            printWriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            printWriter.println("<title>server_automaiton</title>");
            printWriter.println("</head>");

        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(LogUtils.class, "static", e));
        }
    }

    public void printWriterClose() {
        if (printWriter != null) printWriter.close();
    }

    public synchronized static void outErrinfo(Map<String, String> textInfoMap, Exception e) {
        if (e == null) e = new Exception();
        String time = WindosUtils.getDate();
        String separator = StringUtils.leftPad(time, 80 + time.length(), "-");
        separator = StringUtils.rightPad(separator, 80 + separator.length(), "-");
        printWriter.println("<label style=\"color:#FF00FF\"></br><b>" + separator + "</b></br></label>");
        if (textInfoMap != null) {
            for (Map.Entry<String, String> entry : textInfoMap.entrySet()) {
                printWriter.println("<p>");
                printWriter.println("<label style=\"color:3300FF\">");
                printWriter.println("<b>");
                printWriter.println("&nbsp;&nbsp;");
                printWriter.println(entry.getKey());
                printWriter.println(": </b>");
                printWriter.println("</label>");
                printWriter.println("<label style=\"color:990000\">");
                printWriter.println(entry.getValue());
                printWriter.println("</p>");
                printWriter.println("</label>");
            }
        }
        printWriter.println("<label style=\"color:red\">");
        e.printStackTrace(printWriter);
        printWriter.println("</label>");
        printWriter.println("</br>");
    }

    /**
     * 用于将html中的java系统保存进行过滤，加入换行符
     *
     * @return
     */
    public synchronized static boolean logHtmlFormatting() {

        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(LogUtils.getSavelLogFile()), "UTF-8"));
            String msg;
            List<String> list = new ArrayList<>();
            boolean checkLogTitle = false;
            while ((msg = bufferedReader.readLine()) != null) {
                if (!checkLogTitle) {
                    checkLogTitle = true;
                    if (!LOG_TITLE.equals(msg)) return false;

                }

                list.add(msg.replaceAll("at ", "</br>at "));
            }
            list.add(HtmlUtils.colourFormatting(HtmlUtils.getSeparator(150) + "END", HtmlUtils.getRandomColour(), true));
            printWriter = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(LogUtils.getSavelLogFile()), "UTF-8")
                    , true);
            for (String s : list) {
                printWriter.println(s);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(LogUtils.class, "logHtmlFormatting", e));
            return false;
        } finally {
            if (printWriter != null) printWriter.close();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

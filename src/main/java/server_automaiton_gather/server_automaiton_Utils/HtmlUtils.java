package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.WindosUtils;
import com.mfeia.book.server_automaiton.Test;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

import java.io.*;
import java.util.*;

public class HtmlUtils {
    //模板路径
    private static File template;
    //保存路径
    private static File savelLog;
    //随机颜色
    private static List<String> colourList;

    static {
        try {
            colourList = new ArrayList<>();
            colourList.add("339933");
            colourList.add("66CC33");
            colourList.add("99CC33");
            colourList.add("006699");
            colourList.add("000099");
            colourList.add("0000CC");
            colourList.add("FF0000");
            colourList.add("CC00FF");
            colourList.add("FFCCFF");
            template = PropertiesConfig.getConfigFile(PropertiesConfig.HTML_STATIC).getParentFile();
            String log = AutomationUtils.getServerAutomaitonProperties("executive.outcomeslog.save.address").trim();
            log = log.replace("\\", File.separator);
            log = log.replace("/", File.separator);
            savelLog = new File(log);
            File fileParent = savelLog.getParentFile();
            if (!fileParent.exists()) {
                if (!fileParent.mkdirs()) {
                    throw new RuntimeException(fileParent.getPath() + " 创建失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(HtmlUtils.class, "saveCaseToHtml", e));
        }
    }

    public static File getExecutiveOutcomeslogFile() {
        return savelLog;
    }

    public static boolean saveCaseToHtml(PerformInspection performInspection, String date) {
        try {
            //创建一个合适的Configration对象
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            // configuration.setDirectoryForTemplateLoading(new File("C:\\MyWork\\Workspace\\Xijie\\WebRoot\\html"));
            configuration.setDirectoryForTemplateLoading(template);
            configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
            configuration.setDefaultEncoding("UTF-8"); //这个一定要设置，不然在生成的页面中 会乱码
            //获取或创建一个模版。
            Template template = configuration.getTemplate(PropertiesConfig.HTML_STATIC);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("description", "测试开始时间:[" + date + "] 测试结束时间:[" + WindosUtils.getDate() + "]");
            paramMap.put("succeedBranches", RealizePerform.getRealizePerform().getSucceedBranches());
            paramMap.put("failureBranches", RealizePerform.getRealizePerform().getFailureBranches());
            paramMap.put("errLogss", performInspection.getErrLoss());
            paramMap.put("allLogss",  performInspection.getAllLoss());
            Writer writer = new OutputStreamWriter(new FileOutputStream(savelLog), "UTF-8");
            template.process(paramMap, writer);

            System.out.println("html生成成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(HtmlUtils.class, "saveCaseToHtml", e));
            return false;
        }
    }


    public static String colourFormatting(String text, String colour) {
        return colourFormatting(text, colour, false);
    }

    public static String colourFormatting(String text, String colour, boolean deepen) {
        if (deepen) return "<strong><lable style=\"color:" + colour + "\">" + text + "</lable></strong>";
        return "<lable style=\"color:" + colour + "\">" + text + "</lable>";
    }

    /**
     * 获取分隔符
     *
     * @return
     */
    public static String getSeparator(int number) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuffer.append("&nbsp;");
        }
        return stringBuffer.toString();
    }

    public static String getRandomColour() {
        return colourList.get(new Random().nextInt(colourList.size()));
    }
}

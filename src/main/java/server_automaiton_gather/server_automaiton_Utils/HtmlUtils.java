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
    //理智语
    private static List<String> motivational;

    static {
        try {
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
            paramMap.put("succeedLogss", performInspection.getSucceedLoss());
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


    public static String getColourFormatting(String text, String colour) {
        return getColourFormatting(text, colour, false);
    }

    public static String getColourFormatting(String text, String colour, boolean deepen) {
        String c = "<lable style=\"color:" + colour + "\">" + text + "</lable>";
        if (deepen) return "<strong>" + c + "</strong>";
        return c;
    }

    public static String getHtmlHeaders(String title) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        stringBuilder.append("<title>" + title + "</title>");
        stringBuilder.append("</head>");
        return stringBuilder.toString();
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

    public static String getMotivationalRandom() {
        return motivational.get(new Random().nextInt(motivational.size()));
    }

    public static String getErrInfo(String text,int sepaeator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</br>");
        stringBuilder.append(HtmlUtils.getSeparator(sepaeator));
        stringBuilder.append(
                HtmlUtils.getColourFormatting(text, "red", true));
        return stringBuilder.toString();
    }

}

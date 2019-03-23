package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.WindosUtils;
import com.mfeia.book.server_automaiton.AutomationBooksMap;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import server_automaiton_gather.RealizePerform;
import server_automaiton_gather.TestFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlUtils {
    //模板路径
    private static File template;
    //保存路径
    private static File savelLog;

    static {
        template = new File(HtmlUtils.class.getClassLoader().getResource("").getPath());
        savelLog = new File(AutomationUtils.getServerAutomaitonProperties("log.save.address"));
        File fileParent = savelLog.getParentFile();
        if (!fileParent.exists()) fileParent.mkdirs();
    }

    public static boolean saveCaseToHtml(Map<String, List<String>> map, String date) {
        try {
//创建一个合适的Configration对象
            Configuration configuration = new Configuration();
// configuration.setDirectoryForTemplateLoading(new File("C:\\MyWork\\Workspace\\Xijie\\WebRoot\\html"));
            configuration.setDirectoryForTemplateLoading(template);
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            configuration.setDefaultEncoding("UTF-8"); //这个一定要设置，不然在生成的页面中 会乱码
//获取或创建一个模版。
            Template template = configuration.getTemplate("static.html");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("description", "测试开始时间:[" + date + "]测试结束时间:[" + WindosUtils.getDate() + "]");
            paramMap.put("succeedBranches", RealizePerform.getRealizePerform().getSucceedBranches());
            paramMap.put("failureBranches", RealizePerform.getRealizePerform().getFailureBranches());
            paramMap.put("weaponMap", map);
            Writer writer = new OutputStreamWriter(new FileOutputStream(savelLog), "UTF-8");
            template.process(paramMap, writer);

            System.out.println("html生成成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

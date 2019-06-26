package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.JavaUtils;
import server_automaiton_gather.server_automaiton_interface.LogNetworkRequests;

import java.net.URI;
import java.util.*;

public class RecordLogNetworkRequests implements LogNetworkRequests {
    private static RecordLogNetworkRequests recordLogNetworkRequests = new RecordLogNetworkRequests();
    private Map<String, List<Map<String, String>>> networdRequestsMap = new HashMap<>();
    private Map<Double, Map<Class, List<String>>> networdRequestMapList = new LinkedHashMap<>();
    private final String URI = "uri";
    private final String HEADER = "header";
    private final String BODY = "body";

    private RecordLogNetworkRequests() {
    }

    public static RecordLogNetworkRequests getRecordLogNetworkRequests() {
        return recordLogNetworkRequests;
    }

    @Override
    public synchronized String getRequests(double number, Class c) {

//        List<Map<String, String>> list = new ArrayList<>();
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            list = this.networdRequestsMap.get(c);
//            if (list.size() > 1) {
//                stringBuilder.append("此用例存在多条记录，因为共用了同一个标签和类，请自行查询");
//                stringBuilder.append("</br>");
//                stringBuilder.append("\n");
//            }
//            for (Map<String, String> map : list) {
//                if (map.get(URI) != null) stringBuilder.append(JavaUtils.strFormatting(map.get(URI)));
//                if (map.get(HEADER) != null) stringBuilder.append(JavaUtils.strFormatting(map.get(HEADER)));
//                if (map.get(BODY) != null) stringBuilder.append(JavaUtils.strFormatting(map.get(BODY)));
//                stringBuilder.append("/<br>");
//                stringBuilder.append("\n");
//            }
//        } catch (Exception e) {
//
//        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Map<Class, List<String>> classListMap = this.networdRequestMapList.get(number);
            if (classListMap == null) return "NULL";
            if (classListMap.size() == 0) return "\"\"";
            /**
             * 没有找到Class的话，就把全部的标签下内容打印
             */
            if (!classListMap.containsKey(c)) {
                stringBuilder.append("未找到此类的请求【");
                stringBuilder.append(c.toString());
                stringBuilder.append("】");
                stringBuilder.append("，可能此类使用了其他类的请求内容，所以将此类对应的标签全部打印：");
                stringBuilder.append("</br>");
                stringBuilder.append("\n");
                for (Map.Entry<Class, List<String>> entry : classListMap.entrySet()) {
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append("：");
                    stringBuilder.append("</br>");
                    stringBuilder.append("\n");
                    for (String s : entry.getValue()) {
                        stringBuilder.append(s);
                        stringBuilder.append("</br>");
                        stringBuilder.append("\n");
                    }
                }
            } else {
                for (String s : classListMap.get(c)) {
                    stringBuilder.append(s);
                    stringBuilder.append("</br>");
                    stringBuilder.append("\n");
                }
            }
        } catch (Exception e) {

        }


        return stringBuilder.toString();


    }

    @Override
    public synchronized void addRequests(String method, URI uri, Object body, Map<String, String> header, double number, Class c) {
        List<String> list = new ArrayList<>();
        if (this.networdRequestMapList.containsKey(number)) {
            if (this.networdRequestMapList.get(number).containsKey(c)) {
                list = this.networdRequestMapList.get(number).get(c);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【");
        if (uri != null) stringBuilder = addBuilder(stringBuilder, method + "：" + HtmlUtils.getColourFormatting(uri.toString(), "blue"));
        if (body != null) stringBuilder = addBuilder(stringBuilder, BODY + "：" + body.toString());

        if (header != null) stringBuilder = addBuilder(stringBuilder, HEADER + "：" + header.toString());
        stringBuilder.append("】");
        list.add(stringBuilder.toString());
        Map<Class, List<String>> map = new HashMap<>();
        map.put(c, list);
        this.networdRequestMapList.put(number, map);
    }

    private StringBuilder addBuilder(StringBuilder stringBuilder, String string) {
        stringBuilder.append(" [ ");
        stringBuilder.append(string);
        stringBuilder.append(" ] ");
        return stringBuilder;
    }
}

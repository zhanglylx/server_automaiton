package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.JavaUtils;
import server_automaiton_gather.server_automaiton_interface.LogNetworkRequests;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RecordLogNetworkRequests implements LogNetworkRequests {
    private static RecordLogNetworkRequests recordLogNetworkRequests = new RecordLogNetworkRequests();
    private Map<Double, Map<String, String>> networdRequestsMap = new HashMap<>();
    private final String URI = "uri";
    private final String HEADER = "header";
    private final String BODY = "body";

    private RecordLogNetworkRequests() {
    }

    public static RecordLogNetworkRequests getRecordLogNetworkRequests() {
        return recordLogNetworkRequests;
    }

    @Override
    public synchronized String getRequests(double number) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            Map<String, String> map = this.networdRequestsMap.get(number);
            if (map.get(URI) != null) stringBuilder.append(JavaUtils.strFormatting(map.get(URI)));
            if (map.get(HEADER) != null) stringBuilder.append(JavaUtils.strFormatting(map.get(HEADER)));
            if (map.get(BODY) != null) stringBuilder.append(JavaUtils.strFormatting(map.get(BODY)));

        } catch (Exception e) {

        }
        return stringBuilder.toString();

    }

    @Override
    public synchronized void addRequests(String method, URI uri, Object body, Map<String, String> header, double number) {
        Map<String, String> map = new LinkedHashMap<>();
        if (uri != null) map.put(URI, method + "：" + HtmlUtils.getColourFormatting(uri.toString(), "blue"));
        if (body != null) map.put(BODY, BODY + "：" + body.toString());
        if (header != null) map.put(HEADER, HEADER + "：" + header.toString());
        this.networdRequestsMap.put(number, map);
    }
}

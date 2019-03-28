package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.HttpUtils;
import ZLYUtils.NetworkHeaders;
import com.mfeia.book.server_automaiton.Book;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpPost;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.util.HashMap;
import java.util.Map;

import static server_automaiton_gather.server_automaiton_Utils.AutomationUtils.getServerAutomaitonProperties;

public class AutoHttpUtils {
    private static final Map<String, String> mapHeaders = new HashMap<>();

    static {
        mapHeaders.put("version", getServerAutomaitonProperties("version"));
        mapHeaders.put("cnid", getServerAutomaitonProperties(AutomationUtils.CNID).trim());
        mapHeaders.put("uid", getServerAutomaitonProperties("uid").trim());
        mapHeaders.put("appname", getServerAutomaitonProperties("appname").trim());
    }

    public static String doGet(String propertiesPath, String querys, double number) {
        return doGet(getServerAutomaitonProperties(AutomationUtils.getHost()).trim(),
                getServerAutomaitonProperties(propertiesPath).trim(),
                querys, getDoHeaders(querys), number);
    }

    public static String doGet(String propertiesPath, String querys, Map<String, String> headers, double number) {
        return doGet(getServerAutomaitonProperties(AutomationUtils.getHost()).trim(),
                getServerAutomaitonProperties(propertiesPath).trim(),
                querys, headers, number);
    }

    public static String doGet(String host, String path, String querys, double number) {
        return doGet(host, path, querys, getDoHeaders(querys), number);
    }


    public static String doGet(String host, String path, String querys, Map<String, String> headers, double number) {
        NetworkHeaders networkHeaders = new NetworkHeaders();
        Object o = HttpUtils.doGet(HttpUtils.getURI(getUrl(host, path), querys),
                headers,
                networkHeaders);
        return checkResponse(o, networkHeaders, null, number);
    }

    public static String checkResponse(Object response, NetworkHeaders networkHeaders, Object body, double number) {
        if (body == null) body = "NULL";
        if (response instanceof Exception) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(
                            AutoHttpUtils.class, "checkResponseException:[" + networkHeaders.getStatusLine() + "]", (Exception) response,number),number);
            return null;
        } else if (networkHeaders.getStatusLine().getStatusCode() != 200) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(
                            AutoHttpUtils.class, "checkResponse Not 200"
                            , new Exception()
                            , networkHeaders.getHttpRequestBase()
                            , networkHeaders.getStatusLine()
                            , body
                            , number
                    ),number);
        } else if (response == null) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(
                            AutoHttpUtils.class, "checkResponse IS NULL"
                            , new Exception()
                            , networkHeaders.getHttpRequestBase()
                            , networkHeaders.getStatusLine()
                            , body
                            , number
                    ),number);
        }
        return response.toString();
    }


    public static String doPost(String propertiesPath, Object parm, double number) {
        return doPost(getServerAutomaitonProperties(AutomationUtils.getHost()).trim(),
                getServerAutomaitonProperties(propertiesPath).trim(), parm,
                getDoHeaders(parm), number);
    }

    public static String doPost(String host, String propertiesPath, Object parm, double number) {
        return doPost(host,
                getServerAutomaitonProperties(propertiesPath).trim(), parm,
                getDoHeaders(parm), number);
    }


    public static String doPost(String propertiesPath, Object parm, Map<String, String> headers, double number) {
        return doPost(getServerAutomaitonProperties(AutomationUtils.getHost()).trim(),
                getServerAutomaitonProperties(propertiesPath).trim(), parm,
                headers, number);
    }

    public static String doPost(String host, String path, Object parm, Map<String, String> headers, double number) {
        NetworkHeaders networkHeaders = new NetworkHeaders();
        Object o = HttpUtils.doPost(getUrl(host, path), parm,
                headers, networkHeaders);
        return checkResponse(o, networkHeaders, parm, number);

    }

    private static Map<String, String> getDoHeaders(Object querys) {
        if (querys == null || querys.toString().isEmpty()) return mapHeaders;
        Map<String, String> headers = new HashMap<>();
        headers.putAll(mapHeaders);
        if (querys instanceof String) {
            String q = querys.toString().toLowerCase();
            if (!q.contains("uid")) return headers;
            q = q.substring(q.indexOf("uid"));
            if (q.contains("&")) {
                q = q.substring(0, q.indexOf("&"));
            }
            if (q.contains("=")) q = q.substring(q.indexOf("=") + 1);
            headers.put("uid", q);

        } else if (querys instanceof Map) {
            Map<String, String> m = (Map) querys;
            for (Map.Entry<String, String> entry : m.entrySet()) {
                if ("uid".equals(entry.getKey().toLowerCase().trim())) {
                    headers.put("uid", entry.getValue());
                }
            }
        }
        return headers;
    }


    /**
     * 进行host与path拼接
     *
     * @param host
     * @param path
     * @return
     */
    public static String getUrl(String host, String path) {
        if (!host.endsWith("/")) host += "/";
        if (path.startsWith("/")) path = path.substring(1);
        return host + path;
    }

    public static Map<String, String> getMapHeaders() {
        return new HashMap<>(mapHeaders);
    }

    public static Map<String, String> getPostMap(Book book) {
        Map<String, String> map = new HashMap<>(mapHeaders);
        map.put("bookId", book.getBookId() + "");
        return map;
    }


}

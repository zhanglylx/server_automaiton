package com.mfeia.book.server_automaiton;

import ZLYUtils.HttpUtils;
import ZLYUtils.NetworkHeaders;
import com.microsoft.schemas.office.x2006.encryption.CTKeyEncryptor;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

public class AutomationUtils {
    private static final Map<String, String> mapHeaders = new HashMap<>();
    /*
     *配置文件
     */
    private static final String HOST = "host";
    public static final String CNID = "cnid";
    public static final String VERCODE = "vercode";
    public static final String TEL = "tel";

    /*
     * 匹配规则
     */
    private static final Map<String, Object> checkRules = new HashMap<>();
    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String BOOK_ID = "bookId";
    public static final String BOOK_NAME = "bookName";
    public static final String BOOK_INTRO = "intro";
    public static final String BOOK_AUTHOR_NAME = "authorName";
    public static final String BOOK_WORD_COUNT = "wordCount";
    public static final String BOOK_CATEGORY_COLOR = "categoryColor";
    public static final String BOOK_CATEGORY_NAME = "categoryName";
    public static final String BOOK_COVER = "cover";
    public static final String BOOK_DETAIL_AD_URL = "bookDetailAdUrl";
    public static final String AD_IMG_URL = "imgUrl";
    public static final String TAG_URL_DETAIL = "tagUrl";
    public static final String TAG_URL_CLIENT = "tagUrlClient";
    public static final String TAG_IMG_URL = "tagImgUrl";
    public static final String RANK_LIST_AD_URL = "randListAdUrl";
    public static final String TAG_NAME = "tagName";

    private static Properties properties;
    private static ThreadPoolExecutor executorService;

    static {
        properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = AutomationUtils.class.getClassLoader().getResourceAsStream(
                    "config/server_automaiton.properties");
            properties.load(inputStream);
            executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                    Integer.parseInt(properties.getProperty("executorService")));
            mapHeaders.put("version", properties.getProperty("version").trim());
            mapHeaders.put("cnid", properties.getProperty(CNID).trim());
            mapHeaders.put("uid", properties.getProperty("uid").trim());
            mapHeaders.put("appname", properties.getProperty("appname").trim());

        } catch (IOException e) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(AutomationUtils.class, "初始化", e)
            );
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        checkRules.put(BOOK_ID, Pattern.compile("\\d{3,15}"));
        checkRules.put(BOOK_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_INTRO, Pattern.compile(".*"));
        checkRules.put(BOOK_AUTHOR_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_WORD_COUNT, Pattern.compile("\\d+\\.\\d万字"));
        checkRules.put(BOOK_CATEGORY_COLOR, Pattern.compile("#[0-9a-zA-Z]{5,10}"));
        checkRules.put(BOOK_CATEGORY_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_DETAIL_AD_URL,
                Pattern.compile("http://cx\\.ikanshu\\.cn/cx/bookdetail\\?bookid=\\d{1,13}\\$parmurl"));
        checkRules.put(TAG_IMG_URL,
                Pattern.compile("http://imgs\\.ikanshu\\.cn/cx/endimgs/[0-9a-zA-Z]{1,70}\\.((jpg)|(png))"));
        checkRules.put(AD_IMG_URL,
                Pattern.compile("http://imgs\\.ikanshu\\.cn/cx/endimgs/[0-9a-zA-Z]{1,70}\\.((jpg)|(png))"));
        checkRules.put(TAG_URL_DETAIL, Pattern.compile("http://cx\\.ikanshu\\.cn/cx/bookdetail\\?bookid=\\d{1,13}\\$parmurl"));
        checkRules.put(RANK_LIST_AD_URL,
                Pattern.compile("http://cx\\.ikanshu\\.cn/cx/ranklist.*\\?bdid=\\d+\\$parmurl"));
        checkRules.put(TAG_NAME, Pattern.compile(".+"));
        checkRules.put(ID, Pattern.compile("\\d+"));
        checkRules.put(TEXT, Pattern.compile(".+"));
        checkRules.put(TAG_URL_CLIENT,
                Pattern.compile("client://channel_[1-5]"));

    }

    public static String getHeaders(String key) {
        return mapHeaders.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCheckRules(String key) {
        if (checkRules.containsKey(key)) return (T) checkRules.get(key);
        return null;
    }

    public static String getServerAutomaitonProperties(String key) {
        String values = null;
        try {
            values = properties.getProperty(key);
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(AutomationUtils.class,
                            "getServerAutomaitonProperties:key-" + key, e));
        }
        return values;
    }


    public static Map<String, Object> getCheckRulesAll(long bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", getCheckRules(BOOK_ID));
        map.put("bookName", getCheckRules(BOOK_NAME));
        map.put("authorName", getCheckRules(BOOK_AUTHOR_NAME));
        map.put("wordCount", getCheckRules(BOOK_WORD_COUNT));
        map.put("bookImg", getCheckRules(BOOK_COVER, bookId));
        map.put("introduction", getCheckRules(BOOK_INTRO));
        map.put("categoryName", getCheckRules(BOOK_CATEGORY_NAME));
        map.put("categoryColor", getCheckRules(BOOK_CATEGORY_COLOR));
        return map;
    }

    public static Map<String, Object> getCheckRulesAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", getCheckRules(BOOK_ID));
        map.put("bookName", getCheckRules(BOOK_NAME));
        map.put("authorName", getCheckRules(BOOK_AUTHOR_NAME));
        map.put("wordCount", getCheckRules(BOOK_WORD_COUNT));
        map.put("bookImg", null);
        map.put("introduction", getCheckRules(BOOK_INTRO));
        map.put("categoryName", getCheckRules(BOOK_CATEGORY_NAME));
        map.put("categoryColor", getCheckRules(BOOK_CATEGORY_COLOR));
        return map;
    }


    public static Object getCheckRules(String key, long bookId) {
        if (BOOK_COVER.equals(key))
            return "https://cdn.ikanshu.cn/book_covers/" + bookId + ".jpg";
        return null;
    }

    private static String getUrl(String host, String path) {
        if (!host.endsWith("/")) host += "/";
        if (path.startsWith("/")) path = path.substring(1);
        return host + path;
    }

    public static String doGet(String propertiesPath, String querys) {
        return doGet(properties.getProperty(HOST).trim(),
                properties.getProperty(propertiesPath).trim(),
                querys, getDoHeaders(querys));
    }

    public static String doGet(String propertiesPath, String querys, Map<String, String> headers) {
        return doGet(properties.getProperty(HOST).trim(),
                properties.getProperty(propertiesPath).trim(),
                querys, headers);
    }

    public static String doGet(String host, String path, String querys) {
        return doGet(host, path, querys, getDoHeaders(querys));
    }


    public static String doGet(String host, String path, String querys, Map<String, String> headers) {
        NetworkHeaders networkHeaders = new NetworkHeaders();
        try {
            return HttpUtils.doGet(HttpUtils.getURI(getUrl(host, path), querys),
                    headers,
                    networkHeaders);
        } finally {
            if (networkHeaders.getResponseCode() != 200)
                System.out.println(HttpUtils.getURI(getUrl(host, path), querys));
        }
    }


    public static String doPost(String propertiesPath, Object parm) {
        return doPost(properties.getProperty(HOST).trim(),
                properties.getProperty(propertiesPath).trim(), parm,
                getDoHeaders(parm));
    }

    public static String doPost(String host, String propertiesPath, Object parm) {
        return doPost(host,
                properties.getProperty(propertiesPath).trim(), parm,
                getDoHeaders(parm));
    }


    public static String doPost(String propertiesPath,Object parm, Map<String, String> headers) {
        return doPost(properties.getProperty(HOST).trim(),
                properties.getProperty(propertiesPath).trim(), parm,
                headers);
    }

    public static String doPost(String host, String path, Object parm, Map<String, String> headers) {
        NetworkHeaders networkHeaders = new NetworkHeaders();
        try {
            return HttpUtils.doPost(getUrl(host, path), parm,
                    headers, networkHeaders);
        } finally {
            if (networkHeaders.getResponseCode() != 200)
                System.out.println(path);
        }
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

    public static String getHost() {
        return HOST;
    }

    public static Map<String, String> getMapHeaders() {
        return new HashMap<>(mapHeaders);
    }

    public static Map<String, String> getPostMap(Book book) {
        Map<String, String> map = new HashMap<>(mapHeaders);
        map.put("bookId", book.getBookId() + "");
        return map;
    }


    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) throws ClassCastException {
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj, T t) throws ClassCastException {
        return cast(obj);
    }

    public static int getExecutorServiceActiveCount() {
        return executorService.getActiveCount();
    }

    public static void addExecute(Runnable runnable) throws Exception {
        if (!executorService.isShutdown()) {
            executorService.execute(runnable);
        } else {
            throw new Exception("executorService Is power off");
        }

    }

    public static void executorServiceShutdown() {
        if (!executorService.isShutdown())
            executorService.shutdown();
    }

    public static boolean executorServiceisTerminated() {
        return executorService.isTerminated();
    }


}

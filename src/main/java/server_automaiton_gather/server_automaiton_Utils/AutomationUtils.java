package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.HttpUtils;
import ZLYUtils.NetworkHeaders;
import ZLYUtils.WindosUtils;
import com.mfeia.book.server_automaiton.Book;
import com.mfeia.book.server_automaiton.Test;
import org.apache.xmlbeans.impl.tool.Extension;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.*;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

public class AutomationUtils {

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
    public static final String NEW_RANK_LIST_BDID = "cxNewRankListBDID";
    public static final String NEW_CATE_LIST_FIND = "catelistnewFIND";
    public static final String SHU_KU_IMG = "shuKuImg";
    public static final String STICK_SLIDESHOWS_AD_URL = "stickSlideshowsAdUrl";
    private static Properties properties;
    private static ThreadPoolExecutor executorService;

    private static String caseStartTime;

    static {
        caseStartTime = WindosUtils.getDate();

        try {

            properties = PropertiesConfig.getPropertiesConfig(PropertiesConfig.SERVER_AUTOMAITON_CONFIG);
            executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                    Integer.parseInt(properties.getProperty("executorService")));


        } catch (IOException e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(AutomationUtils.class, "初始化", e)
            );
        }
        String cxbUrlPatten = "https?://((cxb-pro)|(cx))\\.((ikanshu)|(cread))\\.((com)|(cn))";
        checkRules.put(STICK_SLIDESHOWS_AD_URL, Pattern.compile(cxbUrlPatten + ".+"));
        checkRules.put(BOOK_ID, Pattern.compile("z?\\d{3,15}"));
        checkRules.put(BOOK_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_INTRO, Pattern.compile(".*"));
        checkRules.put(BOOK_AUTHOR_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_WORD_COUNT, Pattern.compile("[0-9]\\d*\\.?\\d*万?字"));
        checkRules.put(BOOK_CATEGORY_COLOR, Pattern.compile("#[0-9a-zA-Z]{5,10}"));
        checkRules.put(BOOK_CATEGORY_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_DETAIL_AD_URL,
                Pattern.compile(cxbUrlPatten + "/cx/bookdetail\\?bookid=\\d{1,13}\\$parmurl"));
        checkRules.put(TAG_IMG_URL,
                Pattern.compile("http://images-pro\\.cread\\.com/cx/endimgs/[0-9a-zA-Z]{1,70}\\.((jpg)|(png))"));
        checkRules.put(AD_IMG_URL,
                Pattern.compile("http://images-pro\\.cread\\.com/cx/endimgs/[0-9a-zA-Z]{1,70}\\.((jpg)|(png))"));
        checkRules.put(TAG_URL_DETAIL, Pattern.compile(cxbUrlPatten + "/cx/bookdetail\\?bookid=\\d{1,13}\\$parmurl"));
        checkRules.put(RANK_LIST_AD_URL,
                Pattern.compile(cxbUrlPatten + "/cx/ranklist.*\\?bdid=\\d+\\$parmurl"));
        checkRules.put(NEW_RANK_LIST_BDID,
                Pattern.compile(cxbUrlPatten + "/cx/new/rankList.*\\?bdid=\\d+\\$parmurl"));
        checkRules.put(NEW_CATE_LIST_FIND,
                Pattern.compile(cxbUrlPatten + "//cx/new/catelistnew.*\\?flid=\\d+\\$parmurl"));
        checkRules.put(TAG_NAME, Pattern.compile(".+"));
        checkRules.put(ID, Pattern.compile("\\d+"));
        checkRules.put(TEXT, Pattern.compile(".+"));
        checkRules.put(TAG_URL_CLIENT,
                Pattern.compile("client://channel_[1-5]"));
        checkRules.put(SHU_KU_IMG,
                Pattern.compile("https://images-pro\\.cread\\.com/cx/endimgs/[0-9a-zA-Z]{1,70}\\.png"));

    }


    @SuppressWarnings("unchecked")
    public static <T> T getCheckRules(String key) {
        if (checkRules.containsKey(key)) return (T) checkRules.get(key);
        return null;
    }

    public static String getServerAutomaitonProperties(String key) {
        String values = null;
        key = key.trim();
        try {
            values = properties.getProperty(key).trim();
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(AutomationUtils.class,
                            "getServerAutomaitonProperties:key-" + key, e));
        }
        return HttpUtils.getURLDecoderString(values, "UTF-8");
    }


    public static Map<String, Object> getCheckBookAll(String bookId) {
        Map<String, Object> map = new HashMap<>(getCheckBookAll());
        map.put("bookImg", getCheckRules(BOOK_COVER, bookId));
        return map;
    }

    public static Map<String, Object> getCheckBookAll() {
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


    public static Object getCheckRules(String key, String bookId) {
        if (BOOK_COVER.equals(key))
            return Pattern.compile("https:.+/" + bookId + ".jpg");
        return null;
    }


    public static String getHost() {
        return HOST;
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


    public static String getCaseStartTime() {
        return caseStartTime;
    }
}

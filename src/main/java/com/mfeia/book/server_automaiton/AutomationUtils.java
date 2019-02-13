package com.mfeia.book.server_automaiton;

import ZLYUtils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AutomationUtils {
    private static final Map<String, String> mapHeaders = new HashMap<>();
    private static final String HOST = "https://cx.ikanshu.cn/";
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

    static {
        mapHeaders.put("version", "4.2.0");
        mapHeaders.put("cnid", "1062");
        mapHeaders.put("uid", "30916158");
        mapHeaders.put("appname", "cxb");
        checkRules.put(BOOK_ID, Pattern.compile("\\d{3,15}"));
        checkRules.put(BOOK_NAME, Pattern.compile(".+"));
        checkRules.put(BOOK_INTRO, Pattern.compile(".+"));
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

    @SuppressWarnings("unchecked")
    public static <T> T getCheckRules(String key) {
        if (checkRules.containsKey(key)) return (T) checkRules.get(key);
        return null;
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

    public static Object getCheckRules(String key, long bookId) {
        if (BOOK_COVER.equals(key))
            return "https://cdn.ikanshu.cn/book_covers/" + bookId + ".jpg";
        return null;
    }


    public static String doGet(String path, String querys) {
        return HttpUtils.doGet(HOST + path, querys, mapHeaders);

    }

    public static String doPost(String path, Object parm) {
        return HttpUtils.doPost(HOST + path, parm, mapHeaders, null);
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

}

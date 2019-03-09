package com.mfeia.book.server_automaiton.boutique;

import com.mfeia.book.server_automaiton.AutomationBooksMap;
import com.mfeia.book.server_automaiton.AutomationUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.mfeia.book.server_automaiton.Book;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 新书速递
 */
public class RoofNewExpress extends RoofRoot {
    public static final int FLAG = 11;

    public RoofNewExpress() {
        super();
    }


    RoofNewExpress(JSONObject jsonObject,
                   int flag,
                   int show,
                   String name,
                   Pattern actionName,
                   Pattern actionUrl,
                   int showCount) {
        super(jsonObject, flag, show, name, actionName, actionUrl, showCount);
        addJsonMap("showCount", Pattern.compile("[1-9]\\d*"));
    }

    public RoofNewExpress(JSONObject jsonObject) {
        super(jsonObject, FLAG,
                0, "新书速递",
                Pattern.compile("更多新书"),
                AutomationUtils.getCheckRules(
                        AutomationUtils.RANK_LIST_AD_URL
                ), 10);
        addJsonMap("showCount", 10);
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("bookid", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_ID));
        jsonArrayMap.put("newBookName", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_NAME));
        jsonArrayMap.put("intro", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_INTRO));
        jsonArrayMap.put("authorName", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_AUTHOR_NAME));
        jsonArrayMap.put("wordCount", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_WORD_COUNT));
        jsonArrayMap.put("categoryColor", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_CATEGORY_COLOR));
        jsonArrayMap.put("categoryName", AutomationUtils.
                getCheckRules(AutomationUtils.BOOK_CATEGORY_NAME));
        jsonArrayMap.put("cover", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        try {
            Book books = new Book(jsonObject.getInt("bookid"),
                    jsonObject.getString("newBookName"),
                    jsonObject.getString("authorName"),
                    jsonObject.getString("categoryColor"));
            AutomationBooksMap.getAutomationBooksMap().addBooks(books);
            return books.getBookImg();
        } catch (Exception e) {
            this.errException("checkBooks", e);
        }

        return false;

    }

}

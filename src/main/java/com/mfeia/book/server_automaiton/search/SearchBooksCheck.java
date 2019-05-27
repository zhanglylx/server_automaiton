package com.mfeia.book.server_automaiton.search;

import com.mfeia.book.server_automaiton.AutomationBooksMap;
import com.mfeia.book.server_automaiton.Book;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.Map;

public class SearchBooksCheck extends TestFrame {
    private boolean checkIOSID;

    SearchBooksCheck(JSONArray jsonArray, int showCount, TestFrame c, boolean checkIOSID) {
        super();
        setJsonArray(jsonArray);
        setShowCount(showCount);
        setCaseName(c.getClass() + " 中的书籍检查");
        this.checkIOSID = checkIOSID;
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {

    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.BOOK_ID));
        if (this.checkIOSID) jsonArrayMap.put("IOS_id", null);
        if (this.checkIOSID) jsonArrayMap.put("ios_id", null);
        jsonArrayMap.put("name", AutomationUtils.getCheckRules(AutomationUtils.BOOK_NAME));
        jsonArrayMap.put("author", AutomationUtils.getCheckRules(AutomationUtils.BOOK_AUTHOR_NAME));
        jsonArrayMap.put("words", AutomationUtils.getCheckRules(AutomationUtils.BOOK_WORD_COUNT));
        jsonArrayMap.put("categoryName", AutomationUtils.getCheckRules(AutomationUtils.BOOK_CATEGORY_NAME));
        jsonArrayMap.put("cateColor", AutomationUtils.getCheckRules(AutomationUtils.BOOK_CATEGORY_COLOR));
        jsonArrayMap.put("summary", AutomationUtils.getCheckRules(AutomationUtils.BOOK_INTRO));
        jsonArrayMap.put("cover", null);

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        Book book = new Book(JSONObject.fromObject(object).getString("id")
                , JSONObject.fromObject(object).getString("name")
                , JSONObject.fromObject(object).getString("author")
                , JSONObject.fromObject(object).getString("cateColor"));
        AutomationBooksMap.getAutomationBooksMap().addBook(book);
        if ("IOS_id".equals(key) || "ios_id".equals(key))
            return JSONObject.fromObject(object).getString("id");
        if ("cover".equals(key))
            return AutomationUtils.getCheckRules(key, JSONObject.fromObject(object).getString("id"));

        return null;
    }

    @Override
    public void customCheck() {

    }
}

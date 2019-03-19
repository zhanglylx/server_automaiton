package com.mfeia.book.server_automaiton.search;

import com.mfeia.book.server_automaiton.AutomationBooksMap;
import com.mfeia.book.server_automaiton.Book;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 搜索首页
 */
public class SearchHomePage extends TestFrame {
    SearchHomePage(){}
    SearchHomePage(JSONObject jsonObject) {
        super("搜索首页", jsonObject, jsonObject.getJSONArray("hotWords"), 16);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
        jsonArrayMap.put("text", AutomationUtils.getCheckRules(AutomationUtils.TEXT));
        jsonArrayMap.put("type", Pattern.compile("[104]"));
        jsonArrayMap.put("IOS_id", null);
        jsonArrayMap.put("ios_id", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return JSONObject.fromObject(object).getString("id");
    }

    @Override
    public void customCheck() {
    }
}

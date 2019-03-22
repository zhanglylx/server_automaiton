package com.mfeia.book.server_automaiton.search;

import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 搜索结果页
 * 检查搜索第一个结果与关键词匹配
 */
public class SearchResult extends TestFrame {
    private String keyword;

    SearchResult() {
    }

    SearchResult(JSONObject jsonObject, String keyword) {
        super(jsonObject, jsonObject.getJSONArray("list"));
        this.keyword = keyword;
    }

    SearchResult(JSONObject jsonObject) {
        super(jsonObject, jsonObject.getJSONArray("list"));
        this.keyword = null;
    }


    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        if (null == this.keyword) {
            jsonMap.put("count", 0);
            jsonMap.put("pageCount", 0);
        } else {
            jsonMap.put("count", Pattern.compile("[1-9]\\d*"));
            jsonMap.put("pageCount", Pattern.compile("[1-9]\\d*"));
        }

    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        if (null != this.keyword) {
            jsonArrayMap.put("name", null);
        }

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        if (index == 0 && null != this.keyword) return this.keyword;
        return true;
    }

    @Override
    public void customCheck() {

    }
}

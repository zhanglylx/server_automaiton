package com.mfeia.book.server_automaiton.shelf_related;

import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 下拉刷新
 */
public class ShelfUpdate extends TestFrame {
    //用于检查预置的书籍都已经返回在json串中并经过了检查
    private boolean[] b = {false, false};

    ShelfUpdate(JSONObject jsonObject) {
        super(jsonObject, jsonObject.getJSONArray("data"));
        setShowCount(2);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("checkJsonObject", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("count", 1);
        checkMap.put("buyout", 1);
        checkMap.put("status", 3);
        if ("804800013".equals(jsonObject.getString("bookId"))) {
            checkMap.put("lastChapter", "63424606");
            checkMap.put("firstChapter", "63166272");
            this.b[0] = true;
        } else {
            checkMap.put("lastChapter", "69038510");
            checkMap.put("firstChapter", "69038122");
            this.b[1] = true;
        }
        check(jsonObject, checkMap, "检查书架下拉刷新");
        if (index == 1) {
            check(b[0], "", "书籍服务端没有返回804800013更新");
            check(b[1], "", "书籍服务端没有返回80001459更新");
        }

        return true;
    }

    @Override
    public void customCheck() {

    }
}

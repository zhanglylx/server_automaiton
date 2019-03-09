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
    ShelfUpdate(JSONObject jsonObject){
        super(jsonObject,jsonObject.getJSONArray("data"));
    }
    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("checkJsonObject",null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        Map<String,Object> checkMap = new HashMap<>();
        checkMap.put("count",1);
        checkMap.put("buyout",1);
        checkMap.put("status",3);
        if("804800013".equals(jsonObject.getString("bookId"))){
            checkMap.put("lastChapter","63424606");
            checkMap.put("firstChapter","63166272");
        }else{
            checkMap.put("lastChapter","69038510");
            checkMap.put("firstChapter","69038122");
        }
        check(jsonObject,checkMap,"检查书架下拉刷新");
        return true;
    }

    @Override
    public void customCheck() {

    }
}

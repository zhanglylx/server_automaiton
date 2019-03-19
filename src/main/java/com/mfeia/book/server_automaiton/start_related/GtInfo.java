package com.mfeia.book.server_automaiton.start_related;


import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;

import java.util.Map;

/**
 * 获取极光推送
 */
public class GtInfo extends TestFrame {

    GtInfo(JSONObject jsonObject){
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

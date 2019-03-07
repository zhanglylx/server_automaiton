package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

public class DuibaCredut extends DrCallBack {
    DuibaCredut(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("credits", "0");
        jsonMap.put("errorMessage", "appKey不匹配");
        jsonMap.put("status", "fail");
    }
}

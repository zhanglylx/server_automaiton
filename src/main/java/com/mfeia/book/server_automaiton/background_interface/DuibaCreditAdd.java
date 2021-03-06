package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 兑吧增加用户积分
 *
 */
public class DuibaCreditAdd extends DrCallBack {
    DuibaCreditAdd(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("credits", "0");
        jsonMap.put("errorMessage", "appKey不匹配");
        jsonMap.put("status", "fail");
    }
}

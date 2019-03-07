package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;


/**
 * 转发SDK 点击回调接口
 */
public class Finger extends DrCallBack {

    Finger(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("msg", "error");
        jsonMap.put("res", "0");
    }
}

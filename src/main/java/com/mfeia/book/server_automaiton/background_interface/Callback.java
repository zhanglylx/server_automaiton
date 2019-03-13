package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 松果积分墙
 * 检查提交错误信息，服务端返回403
 */
public class Callback extends DrCallBack {

    Callback(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("message", "403");
    }
}

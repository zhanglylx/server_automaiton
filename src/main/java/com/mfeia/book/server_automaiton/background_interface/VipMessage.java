package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 同步小程序vip 信息
 */
public class VipMessage extends DrCallBack {
    VipMessage(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", "1");
        jsonMap.put("info", "FAIL");
        jsonMap.put("toolTip", "");
        jsonMap.put("requestId", "");
        jsonMap.put("data", "null");

    }

}

package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 兑吧兑换结果通知接口
 */
public class DuibaCreditConfirm extends DrCallBack {
    DuibaCreditConfirm(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("message", "");
    }
}

package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 兑吧扣除用户积分
 */
public class DuibaCreditConsume extends DrCallBack {
    DuibaCreditConsume(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("credits", "0");
        jsonMap.put("errorMessage", "sign验证失败");
        jsonMap.put("status", "fail");
    }
}

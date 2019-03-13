package com.mfeia.book.server_automaiton.background_interface;


import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 包月微信支付回调接口
 *
 */
public class WeChatPayCallback extends DrCallBack {
    WeChatPayCallback(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("message", "SUCCESS");
    }

}

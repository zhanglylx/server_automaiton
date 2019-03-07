package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 小程序获取免电用户VIP信息
 *  默认按照不是vip进行检查
 */
public class AppletOrderInfo extends DrCallBack{
    AppletOrderInfo(JSONObject jsonObject) {
        super(jsonObject);
    }
    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        jsonMap.put("info", "SUCCESS");
        jsonMap.put("data","null");
    }
}

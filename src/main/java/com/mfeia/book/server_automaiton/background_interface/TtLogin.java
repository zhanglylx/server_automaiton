package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * QQ登录回调
 * 检查提交错误信息，服务端返回错误提示
 */
public class TtLogin extends  DrCallBack {
    TtLogin(JSONObject jsonObject) {
        super(jsonObject);
    }
    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("message", Pattern.compile(".+(src=\"/static/images/404\\.jpg).+"));
    }
}

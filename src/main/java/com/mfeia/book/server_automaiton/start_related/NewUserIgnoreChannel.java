package com.mfeia.book.server_automaiton.start_related;


import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.Map;

/**
 * 获取新老用户黑白名单
 */
public class NewUserIgnoreChannel extends TestFrame {
    private String userId;

    NewUserIgnoreChannel(JSONObject jsonObject, String userId) {
        super(jsonObject);
        this.userId = userId;
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
        if(AutomationUtils.getServerAutomaitonProperties("uid").equals(this.userId)){
            jsonMap.put("message","用户不存在");
        }
        jsonMap.put("data","{}");
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

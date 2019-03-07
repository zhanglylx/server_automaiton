package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;

public class TaskSynchro extends DrCallBack {
    TaskSynchro(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("success", "false");
        jsonMap.put("errorMessage", "appKey不匹配");
    }
}

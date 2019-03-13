package com.mfeia.book.server_automaiton.background_interface;

import net.sf.json.JSONObject;

import java.util.Map;


/**
 * 兑吧请求的任务同步接口
 * 检查提交错误信息，服务端返回错误提示
 */
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

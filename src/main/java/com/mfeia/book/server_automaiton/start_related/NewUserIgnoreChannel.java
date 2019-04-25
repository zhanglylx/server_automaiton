package com.mfeia.book.server_automaiton.start_related;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 获取新老用户黑白名单
 */
public class NewUserIgnoreChannel extends TestFrame {
    private String userId;
    private boolean isNew;
    NewUserIgnoreChannel(JSONObject jsonObject, String userId,boolean isNew) {
        super(jsonObject);
        this.userId = userId;
        this.isNew = isNew;

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
        if(this.isNew){
            jsonMap.put("isVip","false");
            jsonMap.put("isNew","true");
        }
        if(AutomationUtils.getServerAutomaitonProperties("uid").equals(this.userId)){
            jsonMap.put("message","用户不存在");
            jsonMap.put("data","{}");
        }

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
        if(this.isNew){
            JSONObject jsonObject = this.getJsonObject().getJSONObject("data");
            check(AutomationUtils.getServerAutomaitonProperties("cnid")
                    ,jsonObject.getString("channelId")
                    ,"检查黑白名单中的渠道");
            JSONArray jsonArray = jsonObject.getJSONArray("advId");
            for(int i=0;i<jsonArray.size();i++){
                check(Pattern.compile("GG-\\d+"),jsonArray.getString(i),"检查广告位格式");
            }
        }
    }
}

package com.mfeia.book.server_automaiton.make_money;

import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

public class ReceiveTaskOrReward extends TestFrame {


    private int myTaskId;
    public ReceiveTaskOrReward(JSONObject jsonObject) {
        super(jsonObject);
        this.myTaskId = jsonObject.getInt("myTaskId");
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("status", 1);
        jsonMap.put("code", 0);
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

    public int getMyTaskId() {
        return myTaskId;
    }

}

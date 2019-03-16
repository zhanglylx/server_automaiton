package com.mfeia.book.server_automaiton.make_money;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 赚钱
 */
public class ListEarnIntegralByHd extends TestFrame {
    private int myTaskId;

    public int getMyTaskId() {
        return myTaskId;
    }

    public int getRewardIntegral() {
        return rewardIntegral;
    }


    public int getTaskId() {
        return taskId;
    }

    private int rewardIntegral;
    private int taskId;

    public ListEarnIntegralByHd(JSONObject jsonObject) {
        super(jsonObject);
        this.myTaskId = this.getJsonObject().getInt("myTaskId");
        this.rewardIntegral = this.getJsonObject().getInt("rewardIntegral");
        this.taskId = this.getJsonObject().getInt("taskId");
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }


}

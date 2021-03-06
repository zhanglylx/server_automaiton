package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.*;

/**
 * 排行首页
 */
public class Phindexys extends TestFrame {

    private String[] name = {"男生", "女生", "出版"};
    private Map<String, List<String>> itemId;
    private String actionUrlIdName = AutomationUtils.NEW_RANK_LIST_BDID;

    Phindexys(JSONObject jsonObject, String actionUrlIdName) {
        this(jsonObject);
        this.actionUrlIdName = actionUrlIdName;
    }

    Phindexys(JSONObject jsonObject) {
        super(jsonObject, jsonObject.getJSONArray("data"));
        setShowCount(3);
        this.itemId = new HashMap<>();
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("name", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        check(this.name[index].equals(jsonObject.getString("name")), "预期结果:" + this.name[index] +
                " </br>\n实际结果:" + jsonObject.getString("name"), "检查标题");
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        JSONArray itemJsonArray;

        ArrayList<String> bdidList;
        for (int i = 0; i < jsonArray.size(); i++) {
            itemJsonArray = jsonArray.getJSONObject(i).getJSONArray("item");
            check(itemJsonArray.size() > 0, "预期结果:大于0, 实际结果:" + itemJsonArray.size(), "检查item");
            bdidList = new ArrayList<>();
            for (int itemIndex = 0; itemIndex < itemJsonArray.size(); itemIndex++) {
                check(itemJsonArray.getJSONObject(itemIndex), PaiHangStackRoomConfig.getCheckItemMap(this.actionUrlIdName,false), "检查item");
                try {
                    bdidList.addAll(
                            PaiHangStackRoomConfig.analysisActionUrl(
                                    itemJsonArray.getJSONObject(itemIndex).getString("actionUrl"))
                    );
                } catch (Exception e) {
                    check(e, "analysisActionUrl", "解析analysisActionUrl");
                }

            }
            check(bdidList.size() > 0, "没有获取到ID", "检查item_actionUrl");
            this.itemId.put(jsonObject.getString("name"), bdidList);
        }

        return true;
    }

    @Override
    public void customCheck() {

    }

    public String[] getName() {
        return name;
    }

    public String getActionUrlIdName() {
        return actionUrlIdName;
    }

    Map<String, List<String>> getItemId() {
        return itemId;
    }
}

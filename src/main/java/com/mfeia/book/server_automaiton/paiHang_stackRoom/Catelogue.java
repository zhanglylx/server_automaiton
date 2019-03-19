package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 免追书库
 */
public class Catelogue extends TestFrame {
    public Map<String, List<String>> getThirdCateListId() {
        return thirdCateListId;
    }

    private Map<String, List<String>> thirdCateListId;
    private List<String> name;

    Catelogue(JSONObject jsonObject) {
        super("免追书库", jsonObject, jsonObject.getJSONArray("data"), 3);
        this.thirdCateListId = new HashMap<>();
        this.name = new ArrayList<>();
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        jsonMap.put("info", "SUCCESS");

    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("name", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        String name = jsonObject.getString("name");
        if (this.name.contains(name)) {
            check(false, "存在重复榜单名称:" + name, "检查榜单名称不重复");
        } else {
            this.name.add(name);
        }
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        JSONArray itemJsonArray;
        List<String> list;
        for (int i = 0; i < jsonArray.size(); i++) {
            itemJsonArray = jsonArray.getJSONObject(i).getJSONArray("item");
            check(itemJsonArray, PaiHangStackRoomConfig.getCheckItemMap(AutomationUtils.NEW_CATE_LIST_FIND,true), "检查免追二级分类");
            list = new ArrayList<>();
            for (int itemIndex = 0; itemIndex < itemJsonArray.size(); itemIndex++) {
                check(itemJsonArray.getJSONObject(itemIndex), PaiHangStackRoomConfig.getCheckThirdCateList(), "检查thirdCateList", 1);
                list.add(itemJsonArray.getJSONObject(itemIndex).getString("id"));
            }
            check(list.size() > 0, "thirdCateList获取为空", "检查thirdCateList是否获取到ID");
            if (this.thirdCateListId.containsKey("name")) {
                List<String> list1 = new ArrayList<>(this.thirdCateListId.get(name));
                list1.addAll(list);
            } else {
                this.thirdCateListId.put(name, list);
            }
        }
        return true;
    }

    @Override
    public void customCheck() {

    }
}

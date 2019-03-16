package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import com.mfeia.book.server_automaiton.boutique.RoofNewExpress;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 书库二级
 */
public class CateListNew extends RoofNewExpress {


    //用于筛选条件
    private List<String> thirdCateListId;
    CateListNew(JSONObject jsonObject,String query) {
        super();
        this.setJsonObject(jsonObject);
        this.setJsonArray(jsonObject.getJSONArray("dataList"));
        this.setShowCount(1);
        this.thirdCateListId = new ArrayList<>();
        this.setCaseName(query);

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
    }

    @Override
    public void customCheck() {
        JSONArray jsonArray = getJsonObject().getJSONObject("cateListTwo").getJSONArray("thirdCateList");
        List<String> list = new ArrayList<>();
        Map<String,Object> checkMap = new HashMap<>();
        checkMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
        checkMap.put("name", Pattern.compile(".{2,6}"));
        check(jsonArray,checkMap,"检查thirdCateList",1);
        for(int i=0;i<jsonArray.size();i++){
            list.add((jsonArray.getJSONObject(i).getString("id")));
        }
        this.thirdCateListId.addAll(list);
    }

    public List<String> getThirdCateListId() {
        return thirdCateListId;
    }
}

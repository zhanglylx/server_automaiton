package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import com.mfeia.book.server_automaiton.boutique.RoofNewExpress;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 排行二级页
 */
public class NewRankList extends RoofNewExpress {
    NewRankList(JSONObject jsonObject){
        super();
        this.setJsonObject(jsonObject);
        this.setJsonArray(jsonObject.getJSONObject("data").getJSONArray("dataList"));
        this.setShowCount(20);

    }
    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
        jsonMap.put("info","SUCCESS");
    }

}

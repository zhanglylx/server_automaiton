package com.mfeia.book.server_automaiton.boutique;

import com.mfeia.book.server_automaiton.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RoofFlagSeven extends RoofRoot {
    public static final int FLAG = 7;
    private Map<String, String> map;

    public RoofFlagSeven(JSONObject jsonObject) {
        super(jsonObject, FLAG,
                0,
                jsonObject.getString("name"),
                Pattern.compile(""),
                Pattern.compile("readBook:\\d+.+"),
                1);
        this.map = new HashMap<>();
        this.map.put("name", jsonObject.getString("name"));
        this.map.put("targetUrl", jsonObject.getString("actionUrl"));
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonMap) {
        jsonMap.put("name", null);
        jsonMap.put("targetUrl", null);
        jsonMap.put("img", AutomationUtils.getCheckRules(AutomationUtils.AD_IMG_URL));
        jsonMap.put("text", AutomationUtils.getCheckRules(AutomationUtils.TEXT));
    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        return this.map.get(key);
    }

}

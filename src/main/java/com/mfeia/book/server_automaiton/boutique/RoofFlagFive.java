package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

public class RoofFlagFive extends RoofRoot{
    public static final int FLAG=5;
    public RoofFlagFive(JSONObject jsonObject){
        super(jsonObject,
                FLAG,
                1,
                jsonObject.getString("name"),
                Pattern.compile(jsonObject.getString("name")),
                Pattern.compile(".+"),
                1
                );
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonMap) {
        jsonMap.put("adUrl", Pattern.compile(".+"));
        jsonMap.put("adImgUrl", AutomationUtils.getCheckRules(AutomationUtils.TAG_IMG_URL));
        jsonMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
    }
}

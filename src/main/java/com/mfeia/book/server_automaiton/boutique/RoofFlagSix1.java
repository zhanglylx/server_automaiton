package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 小banner
 */
public class RoofFlagSix1 extends RoofRoot {
    public static final int FLAG = 6;

    public RoofFlagSix1(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                1,
                "小banner",
                Pattern.compile("小banner"),
                Pattern.compile(""),
                4);
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("adUrl", AutomationUtils.getCheckRules(
                AutomationUtils.RANK_LIST_AD_URL));
        jsonArrayMap.put("adImgUrl", AutomationUtils.getCheckRules(
                AutomationUtils.AD_IMG_URL
        ));
        jsonArrayMap.put("id", AutomationUtils.getCheckRules(
                AutomationUtils.ID
        ));
    }

}

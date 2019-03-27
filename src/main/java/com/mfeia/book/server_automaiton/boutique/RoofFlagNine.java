package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 热门标签
 */
public class RoofFlagNine extends RoofRoot {
    public static final int FLAG = 9;

    public RoofFlagNine(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                0,
                "热门标签",
                Pattern.compile("换一换"),
                Pattern.compile(""),
                4);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonMap) {
        jsonMap.put("tagName", AutomationUtils.getCheckRules(AutomationUtils.TAG_NAME));
        jsonMap.put("tagUrl", AutomationUtils.getCheckRules(AutomationUtils.TAG_URL_DETAIL));
        jsonMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
    }

}

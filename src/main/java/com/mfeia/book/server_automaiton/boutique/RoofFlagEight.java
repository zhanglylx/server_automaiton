package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 精品顶部5图
 */
public class RoofFlagEight extends RoofRoot {
    public static final int FLAG = 8;
    private static final String[] TAB_NAME_ARRAYS =
            new String[]{"男频", "女频", "完本", "新书", "出版"};

    public RoofFlagEight(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                1,
                jsonObject.getString("name"),
                Pattern.compile(""),
                Pattern.compile(""),
                TAB_NAME_ARRAYS.length);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonMap) {
        jsonMap.put("id", AutomationUtils.getCheckRules(
                AutomationUtils.ID));
        jsonMap.put("tagUrl", AutomationUtils.getCheckRules(
                AutomationUtils.TAG_URL_CLIENT));
        jsonMap.put("tagImgUrl", AutomationUtils.getCheckRules(
                AutomationUtils.TAG_IMG_URL));
        jsonMap.put("tagName", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return TAB_NAME_ARRAYS[index];
    }


}

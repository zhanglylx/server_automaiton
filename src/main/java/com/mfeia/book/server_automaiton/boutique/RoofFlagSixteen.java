package com.mfeia.book.server_automaiton.boutique;

import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 更多，图书3+3
 */
public class RoofFlagSixteen extends RoofRoot {
    public static final int FLAG = 16;

    public RoofFlagSixteen(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                0,
                "图书3\\+3",
                Pattern.compile("更多"),
                Pattern.compile("null"),
                2);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("list", null);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        TestFrame roofNewExpress = new RoofNewExpress(JSONObject.fromObject(object),
                -1,
                0,
                ".+(" + this.getClass().getName() + " 引用){0}",
                Pattern.compile("更多"),
                AutomationUtils.getCheckRules(
                        AutomationUtils.RANK_LIST_AD_URL),
                3

        ).stratCheck();
        return roofNewExpress.checkCaseResult();
    }

}

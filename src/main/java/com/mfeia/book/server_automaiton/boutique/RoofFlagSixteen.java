package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.TestFrame;
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
        //FLAG=16中包含list数组，每个数组的FLAG为-1
        TestFrame roofNewExpress = new RoofNewExpress(JSONObject.fromObject(object),
                -1,
                0,
                ".+(" + this.getClass().getName() + " 引用){0}",
                Pattern.compile("更多"),
                AutomationUtils.getCheckRules(
                        AutomationUtils.RANK_LIST_AD_URL),
                3

        ).stratCheck();
        check(roofNewExpress.checkCaseResult(), "使用匿名类检查，错误已打印", "检查图书3+3中的更多");
        return true;
    }

}

package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 第2个小banner
 */
public class RoofFlagSix2 extends RoofRoot {

    public RoofFlagSix2(JSONObject jsonObject) {
        super(jsonObject,
                RoofFlagSix1.FLAG,
                1,
                "小banner",
                Pattern.compile("小banner"),
                Pattern.compile(""),
                4);
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonMap) {
        jsonMap.put("adUrl", Pattern.compile(
                AutomationUtils.getCheckRules(
                        AutomationUtils.BOOK_DETAIL_AD_URL) +
                "|" +
                "(http://m\\.cread\\.com/cp\\.aspx\\?id=\\d+)"));
        jsonMap.put("adImgUrl", AutomationUtils.getCheckRules(
                AutomationUtils.AD_IMG_URL));
                jsonMap.put("id", AutomationUtils.getCheckRules(
                        AutomationUtils.ID));
    }
}

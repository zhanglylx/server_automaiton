package com.mfeia.book.server_automaiton.boutique;

import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.regex.Pattern;

/**
 * 8图版式
 */
public class RoofFlagZero extends RoofNewExpress {
    public static final int FLAG = 0;


    public RoofFlagZero(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                0,
                jsonObject.getString("name"),
                Pattern.compile(".*"),
                Pattern.compile("("+AutomationUtils.getCheckRules(AutomationUtils.RANK_LIST_AD_URL)+")?"),
                8);
    }
}

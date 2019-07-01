package com.mfeia.book.server_automaiton.boutique;

import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.regex.Pattern;

public class RoofFlagOne extends RoofNewExpress {
    public static final int FLAG = 1;

    public RoofFlagOne(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                0,
                jsonObject.getString("name"),
                Pattern.compile(".*"),
                Pattern.compile("("+AutomationUtils.getCheckRules(AutomationUtils.RANK_LIST_AD_URL)+")?"),
                1
        );
    }
}

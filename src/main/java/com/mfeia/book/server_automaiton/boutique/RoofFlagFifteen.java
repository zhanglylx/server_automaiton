package com.mfeia.book.server_automaiton.boutique;

import com.mfeia.book.server_automaiton.AutomationUtils;
import net.sf.json.JSONObject;

import java.util.regex.Pattern;


/**
 * 文学经典
 */
public class RoofFlagFifteen extends RoofNewExpress {
    public static final int FLAG = 15;

    public RoofFlagFifteen(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                0,
                "文学经典",
                Pattern.compile("更多"),
                AutomationUtils.getCheckRules(
                        AutomationUtils.RANK_LIST_AD_URL
                ),
                5
        );
    }
}

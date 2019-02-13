package com.mfeia.book.server_automaiton.boutique;

import net.sf.json.JSONObject;

import java.util.regex.Pattern;

public class RoofFlagOne extends RoofNewExpress {
    public static final int FLAG = 1;

    public RoofFlagOne(JSONObject jsonObject) {
        super(jsonObject,
                FLAG,
                0,
                jsonObject.getString("name"),
                Pattern.compile(""),
                Pattern.compile(""),
                jsonObject.getInt("showCount") == 3 ? 3 : 10
        );
    }
}

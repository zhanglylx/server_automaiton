package com.mfeia.book.server_automaiton.boutique;

import net.sf.json.JSONObject;

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
                Pattern.compile(""),
                Pattern.compile(""),
                8);
    }
}

package com.mfeia.book.server_automaiton.boutique;

import net.sf.json.JSONObject;

import java.util.regex.Pattern;

/**
 * 书友再看
 */
public class RoofFlagFourteen extends RoofNewExpress {
    public static final int FLAG = 14;


    public RoofFlagFourteen(JSONObject jsonObject) {
        super(jsonObject, FLAG,
                0,
                jsonObject.getString("name"),
                Pattern.compile("换一换"),
                Pattern.compile("client://refresh_text_tag"),
                4
        );
    }
}

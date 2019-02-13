package com.mfeia.book.server_automaiton.boutique;

import com.mfeia.book.server_automaiton.TestFrame;

import java.util.Map;

/**
 * 热门标签
 * 换一换
 */
public class ChangeTag extends ChangeBook {
    /**
     * 需要与标签相同的换一换
     * @param testFrame
     */
    public ChangeTag(TestFrame testFrame, double number) {
        super(number);
        try {
            statr(testFrame,
                    "cx/exchange",
                    "bdId=" + testFrame.getJsonObject().getInt("id"),
                    null,
                    "list");
        } catch (Exception e) {
            errException("解析", e);
        }
    }

    /**
     * 标签换一换
     */
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
    }

}

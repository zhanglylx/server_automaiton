package com.mfeia.book.server_automaiton.jenkins_build;

import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 获取图书章节内容
 */
public class Getchapter extends TestFrame {
    public Getchapter(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("content", AutomationUtils.getCheckRules(AutomationUtils.TEXT));
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

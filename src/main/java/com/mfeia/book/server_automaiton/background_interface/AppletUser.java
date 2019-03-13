package com.mfeia.book.server_automaiton.background_interface;

import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序获取免电用户信息
 * 检查结果正确性
 */
public class AppletUser extends TestFrame {
    private String tel;

    AppletUser(JSONObject jsonObject, String tel) {
        super(jsonObject);
        this.tel = tel;
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        jsonMap.put("info", "SUCCESS");
        Map<String, String> map = new HashMap<>();
        map.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
        map.put("tel",String.valueOf(this.tel));
        check(this.getJsonObject().getJSONObject("data"), map, "检查用户id");
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }

}

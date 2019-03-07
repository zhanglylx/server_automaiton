package com.mfeia.book.server_automaiton.background_interface;

import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 点入广告回调接口
 */
public class DrCallBack extends TestFrame {

    DrCallBack(JSONObject jsonObject) {
        super(jsonObject);

    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("success", "true");
        jsonMap.put("message", "成功");
    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

package com.mfeia.book.server_automaiton;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 用于打印错误输出
 */
public class ErrException extends TestFrame {
    public ErrException(String errText, Exception e,double number) {
        this.setTag(number);
        errException(errText, e);
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {

    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        return null;
    }
}

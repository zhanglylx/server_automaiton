package com.mfeia.book.server_automaiton;

import java.util.Map;

/**
 * 用于打印错误输出
 */
public class ErrException extends TestFrame {
    public ErrException(Class c , Object errText, Exception e, double number) {
        this.setTag(number);
        errException(c.getName()+" : "+errText, e);
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

package server_automaiton_gather;

import server_automaiton_gather.TestFrame;

import java.util.Map;

/**
 * 用于打印错误输出
 */
public class ErrException extends TestFrame {
    public ErrException(Class c , Object errText, Exception e, double number) {
        this.setTag(number);
        errException(c.getName()+" : "+errText, e);
    }
    public ErrException(Class c , Object errText, Exception e) {
        this.setTag(-1);
        errException(c.getName()+" : "+errText, e);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

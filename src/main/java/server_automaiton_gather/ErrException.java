package server_automaiton_gather;

import ZLYUtils.JavaUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;

import java.util.Map;

/**
 * 用于打印错误输出
 */
public class ErrException extends TestFrame {
    public static final int defaultNumber = -1;

    public ErrException(Class c, Object errText, Exception e, double number) {
        this.setTag(number);
        this.setCaseName(JavaUtils.strFormatting(c.getName()
                , errText.toString()
                , e.toString()));
        errException(e.toString(), e);
    }

    /**
     * 网络错误
     *
     * @param c
     * @param errText
     * @param e
     * @param httpRequestBase
     * @param statusLine
     */
    public ErrException(Class c
            , Object errText
            , Exception e
            , HttpRequestBase httpRequestBase
            , StatusLine statusLine
            , Object requestBody,
                        double number) {
        this(c, JavaUtils.strFormatting(errText.toString()
                , httpRequestBase.toString()
                , "ResponseStatus:" + statusLine.toString()
                ,"body:"+requestBody), e,number);
    }

    public ErrException(Class c, Object errText, Exception e) {
        this(c, errText, e, defaultNumber);
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

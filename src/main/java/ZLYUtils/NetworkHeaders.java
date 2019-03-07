package ZLYUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息头类型
 */
public class NetworkHeaders {
    private static NetworkHeaders networkHeaders;
    private Map<String,List<String>> headers;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    private int responseCode;
    public NetworkHeaders(){
        this.headers = new HashMap<>();
        this.responseCode = -1;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }


}

package ZLYUtils;


import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicStatusLine;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息头类型
 */
public class NetworkHeaders {
    private Map<String, List<String>> headers;
    private long startTime;
    private long endTime;
    private StatusLine statusLine;

    public NetworkHeaders() {
        this.headers = new HashMap<>();
        this.statusLine = new BasicStatusLine(new ProtocolVersion("NULL", 0, 0), 0, "NULL");
        this.startTime = 0;
        this.endTime = Long.MAX_VALUE;
    }

    public long getResponseMillisecondsTime() {
        return this.endTime - this.startTime;
    }

    public HttpRequestBase getHttpRequestBase() {
        return httpRequestBase;
    }

    void setHttpRequestBase(HttpRequestBase httpRequestBase) {
        this.httpRequestBase = httpRequestBase;
    }

    private HttpRequestBase httpRequestBase;


    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }
}

package server_automaiton_gather.server_automaiton_interface;

import java.net.URI;
import java.util.Map;

/**
 * 记录请求和获取请求
 */
public interface LogNetworkRequests {
    String getRequests(double number,Class c);
    void addRequests(String method,URI uri,Object body, Map<String,String> header,double number,Class c);
}

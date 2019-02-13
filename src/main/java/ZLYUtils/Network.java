package ZLYUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class Network {
    public static String networkUrl;
    public static String appname;
    public static String brand;
    public static String imei;
    public static String mac;
    public static String model;
    public static String oscode;
    public static String other;
    public static String packname;
    public static String platform;
    public static String uid;
    public static String umeng;
    public static String vcode;
    public static String version;
    public static String cnid;

    static {
        initializeHeaders();
    }

    /**
     * 初始化消息头
     */
    public static void initializeHeaders() {
        appname = "mfzs";
        brand = "Samsung";
        imei = UUID.randomUUID().toString();
        mac = "asdfsadfsadfdfsadfadf4324342432";
        model = "zhanglianyu";
        oscode = "24";
        other = "a";
        packname = "com.mianfeia.book";
        platform = "android";
        uid = "123456";
        umeng = "FreeShu_mpxxzs9";
        vcode = "68";
        version = "4.1.0";
        cnid = "1500";

    }

    public static HttpURLConnection setHeaders(HttpURLConnection connection) {
        if (connection == null) throw new IllegalArgumentException("connection为空");
        connection.setRequestProperty("appname", appname);
        connection.setRequestProperty("brand", brand);
        connection.setRequestProperty("imei", imei);
        connection.setRequestProperty("mac", mac);
        connection.setRequestProperty("model", model);
        connection.setRequestProperty("oscode", oscode);
        connection.setRequestProperty("other", other);
        connection.setRequestProperty("packname", packname);
        connection.setRequestProperty("platform", platform);
        connection.setRequestProperty("uid", uid);
        connection.setRequestProperty("umeng", umeng);
        connection.setRequestProperty("vcode", vcode);
        connection.setRequestProperty("version", version);
        connection.setRequestProperty("cnid", cnid);
        return connection;
    }

    public static String sendGet(String url) {
        if (url == null) throw new IllegalArgumentException("url参数为空");
        return sendGet(url, "");
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param param
     * @return 服务响应内容, null为响应非200
     */
    public static String sendGet(String url, String param) {
        LocalProxy();
        if (url == null) throw new IllegalArgumentException("url参数为空");
        if (param == null) throw new IllegalArgumentException("param参数为空");
        StringBuffer data = new StringBuffer();
        ;
        BufferedReader in = null;
        String urlStr = url + "?" + param;
        if (param.equals("")) urlStr = url;
        HttpURLConnection connection = null;
        try {
            networkUrl = url;
            // 打开和URL之间的连接
            connection = (HttpURLConnection) new URL(urlStr).openConnection();
            connection = setHeaders(connection);
            /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("GET");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(false);
//            // 设置超时时间
//            connection.setConnectTimeout(3000);


            // 设置通用的请求属性
            connection.setRequestProperty("Accept-Encoding", "chunked");
            connection.setRequestProperty("Charset", "utf-8");
            // 不能使用缓存
            connection.setUseCaches(false);
            // 建立实际的连接
            connection.connect();
            int getResponseCode = ((HttpURLConnection) connection).getResponseCode();
            if (getResponseCode != 200) {// 检查服务器响应
                return getResponseCode + "";
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                data.append(line);
            }

        } catch (Exception e) {
            data.append("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }

            initializeHeaders();
        }
        return data.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 本方法暂不支持GZIP解压，所以没有设置Accept-Encoding
     * 默认为创新版
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @author ZhangLianYu
     */
    public static String sendPost(String url, String param) {
        LocalProxy();
        if (url == null) throw new IllegalArgumentException("url参数为空");
        if (param == null) throw new IllegalArgumentException("param参数为空");
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        networkUrl = url;
        HttpURLConnection connection = null;
        try {
            // 打开和URL之间的连接
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection = setHeaders(connection);
            /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("POST");
            // 设置是否使用缓存
            connection.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(false);
//            // 设置超时时间
//            connection.setConnectTimeout(3000);

            // 设置通用的请求属性
            ((HttpURLConnection) connection).setInstanceFollowRedirects(false);// 302重定向
            connection.setRequestProperty("Content-Length", String.valueOf(param.length()));
            connection.setRequestProperty("Accept-Encoding", "chunked");
            connection.setRequestProperty("Charset", "utf-8");
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 建立实际的连接
            connection.connect();
//            Map<String, List<String>> map = conn.getHeaderFields();
////            遍历所有的响应头字段
//            System.out.println(map.get("Charset"));
            int getResponseCode = ((HttpURLConnection) connection).getResponseCode();
            if (getResponseCode != 200) {// 检查服务器响应
                return getResponseCode + "";
            }
//            if (getResponseCode == 302) {
//                // 如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
//                String location = conn.getHeaderField("Location");
//                //StringText cookies = conn.getHeaderField("Set-Cookie");
//                if ("/".equals(location)) {
//                    location = "http://jira.ffan.biz" + location;
//                }
//                realUrl = new URL(location);
//                conn = (HttpURLConnection) realUrl.openConnection();
//                conn.addRequestProperty("Accept-Charset", "UTF-8;");
//                conn.setRequestProperty("accept", "*/*");
//                conn.addRequestProperty("User-Agent", userAgent);
//                conn.setRequestProperty("Accept-Encoding", "chunked");
//                conn.connect();
//                System.out.println("跳转地址:" + location);
//                map = conn.getHeaderFields();
//                //	setCookies(map);
//            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            result.append("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (connection != null) connection.disconnect();
            initializeHeaders();
        }
        return result.toString();

    }

    /**
     * 设置本地代理
     */
    public static void LocalProxy() {
        System.setProperty("http.proxySet", "true"); //将请求通过本地发送
        System.setProperty("http.proxyHost", "127.0.0.1");  //将请求通过本地发送
        System.setProperty("http.proxyPort", "8888"); //将请求通过本地发送
    }

    /**
     * 转码
     *
     * @param param
     * @return
     */
    public static String getEncoderString(String param) {
        param = param.replace("\n", "\r\n");
        try {
            param = java.net.URLEncoder.encode(param, HttpUtils.CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return param;
    }

    /**
     * 转码
     *
     * @param param
     * @return
     */
    public static String getEncoderString(String param, String encoder) {
        param = param.replace("\n", "\r\n");
        try {
            param = java.net.URLEncoder.encode(param, encoder);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return param;
    }

    /**
     * URL 解码
     *
     * @return StringText
     * @author zhanglianyu
     * @date 2017.7.23
     */
    public static String getURLDecoderString(String str, String encodingName) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            str = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            str = str.replaceAll("\\+", "%2B");
            result = java.net.URLDecoder.decode(str, encodingName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}

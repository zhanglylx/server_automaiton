package server_automaiton_gather;

import ZLYUtils.JavaUtils;
import com.mfeia.book.server_automaiton.Test;
import org.apache.log4j.Logger;
import server_automaiton_gather.server_automaiton_Utils.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;


import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 测试框架
 */
public abstract class TestFrame {
    private boolean[] checkBoolean = new boolean[0];
    private Map<String, Object> jsonArrayMap = new HashMap<>();
    private Map<String, Object> jsonMap = new HashMap<>();
    private String object1 = null;
    private String object2 = null;
    private String caseName = this.getClass().getName();
    private JSONArray jsonArray = new JSONArray();
    private JSONObject jsonObject = new JSONObject();
    private Map<String, Object> logJsonObjectMap = new HashMap<>();
    private Map<String, Object> logJsonArrayMap = new HashMap<>();
    private String logJsonArray = "";
    private JSONObject logJsonObject = new JSONObject();
    private double tag = -1;
    private int showCount = 0;
    private boolean isPrintLog = false;
    private static Logger logger = Logger.getLogger(TestFrame.class);

    public TestFrame() {
        setCaseName("Default Constructor");
    }

    public TestFrame(JSONObject jsonObject) {
        setCaseName("JSONObject Constructor");
        this.jsonObject = jsonObject;
    }

    public TestFrame(JSONObject jsonObject, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.jsonObject = jsonObject;
        setCaseName("JSONObject & jsonArray Constructor");
    }

    public TestFrame(String caseName, JSONObject jsonObject, JSONArray jsonArray, int showCount) {
        setCaseName(caseName);
        this.jsonObject = jsonObject;
        this.jsonArray = jsonArray;
        this.showCount = showCount;
    }

    public abstract void settingJsonMap(Map<String, Object> jsonMap);

    public abstract void settingJsonArrayMap(Map<String, Object> jsonArrayMap);

    //如果实现内部已经自定义检查，请将返回值设为;true
    public abstract Object customCheckJsonArrayObject(Object object, int index, String key, int size);

    //自定义
    public abstract void customCheck();

    public TestFrame stratCheck() {
        try {
            loadJsonMap();
            check(this.jsonObject, "stratCheckJsonObject");
            loadJsonArrayMap();
            check(this.jsonArray, "stratCheckJsonArray");

        } catch (Exception e) {
            errException("stratCheck", e);
        }
        try {
            customCheck();
        } catch (Exception e) {
            errException("stratCheck : customCheck", e);
        }
        return this;
    }

    /**
     * 检查数量是否符合要求
     *
     * @param showCount
     */
    public void checkJsonArrayShowCount(JSONArray jsonArray, int showCount) {
        if (showCount > 0) {
            if (jsonArray.size() < showCount)
                check(false, jsonArray.size(),
                        "检查数量checkJsonArrayShowCount是否符合要求:不符合，预期数量:"
                                + showCount
                                + "实际数量:" + jsonArray.size());
        }
    }

    public final void check(Object object1, Object object2, String testCase) {
        check(object1, object2, testCase, this.showCount);
    }

    public final void check(Object object1, Object object2, String testCase, int showCount) {

        try {
            this.object1 = object1.toString();
            this.object2 = object2.toString();
            if (object1 instanceof String) {
                check(this.object1, this.object2, testCase);
            } else if (object1 instanceof Integer) {
                check(Integer.parseInt(this.object1), Integer.parseInt(this.object2), testCase);
            } else if (object1 instanceof Long) {
                check(Long.parseLong(this.object1), Long.parseLong(this.object2), testCase);
            } else if (object1 instanceof Pattern) {
                check(AutomationUtils.cast(object1, Pattern.compile("")), this.object2, testCase);
            } else if (object1 instanceof Boolean) {
                this.object1 = "expression：" + object1;
                check(AutomationUtils.cast(object1, Boolean.FALSE), testCase);
            } else if (object1 instanceof JSONObject && object2 instanceof Map) {
                check((JSONObject) (object1), AutomationUtils.cast(object2), testCase);
            } else if (object1 instanceof JSONArray && object2 instanceof Map) {
                check((JSONArray) (object1), AutomationUtils.cast(object2), testCase, showCount);
            } else if (object1 instanceof Exception) {
                errException("testCase:{" + testCase + "}\n预期结果:{" + object2 + "}", AutomationUtils.cast(object1));
            } else {
                errException(false, "[" + testCase + "]未能解析");
            }
        } catch (Exception e) {
            errException(testCase, e);
        }
    }

    /**
     * 检查字符串是否相等
     *
     * @param expected 预期结果
     * @param str2     实际结果
     * @param testCase 测试用例名称
     */
    private void check(String expected, String str2, String testCase) {
        errException(expected.equals(str2), testCase);
    }

    private void check(JSONObject jsonObject, Map<String, Object> map, String testCaseName) {
        this.logJsonObject = jsonObject;
        this.logJsonObjectMap = map;
        if (checkJson(jsonObject, map, testCaseName)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                try {
                    check(entry.getValue(),
                            jsonObject.getString(entry.getKey()),
                            testCaseName +
                                    ": " +
                                    getJsonTestCase(jsonObject, entry));
                } catch (Exception e) {
                    errException(testCaseName +
                            ": " + entry.getKey(), e);
                }
            }
        }
    }

    /**
     * 用于外部调用时，直接返回判断结果
     *
     * @param b        预期结果
     * @param testCase 测试用例名称
     */
    private void check(boolean b, String testCase) {
        errException(b, testCase);
    }

    /**
     * 检查int是否相等
     *
     * @param expected 预期结果
     * @param int2     实际结果
     * @param testCase 测试用例名称
     */
    private void check(int expected, int int2, String testCase) {
        errException(expected == int2, testCase);
    }

    /**
     * 检查long是否相等
     *
     * @param expected
     * @param long2
     * @param testCase
     */
    private void check(Long expected, Long long2, String testCase) {
        errException(expected.equals(long2), testCase);
    }

    /**
     * 检查字符串是否满足正则表达式
     *
     * @param pattern  正则表达式
     * @param str2     被检查的字符串
     * @param testCase 测试用例名称
     */
    private void check(Pattern pattern, String str2, String testCase) {
        str2 = JavaUtils.replaceLineBreak(str2, "");
        errException(pattern.matcher(str2).matches(), testCase);
    }

    /**
     * 解析json
     *
     * @param jsonObject json串
     */
    private void check(JSONObject jsonObject, String testCaseName) {
        check(jsonObject, this.jsonMap, testCaseName);
    }

    /**
     * 错误打印格式
     *
     * @param json  需要坚持的json
     * @param entry map迭代器
     * @return case文本
     */
    private String getJsonTestCase(JSON json, Map.Entry<String, Object> entry) {
        if (json instanceof JSONObject) {
            return "[JSON:\"" + entry.getKey() + "\"]";
        } else {
            return "[JSONARRAYS:\"" + entry.getKey() + "\"]";
        }

    }


    private void loadJsonArrayMap() {
        try {
            settingJsonArrayMap(this.jsonArrayMap);
        } catch (Exception e) {
            errException(false,
                    "[settingJsonArray]解析异常:["
                            + this.caseName + "]"
                            + e.toString());
        }
    }

    /**
     * 加载jsonMap
     */
    private void loadJsonMap() {
        try {
            settingJsonMap(this.jsonMap);
        } catch (Exception e) {
            errException(false,
                    "[settingJsonMap]解析异常:["
                            + this.caseName + "]"
                            + e.toString());
        }
    }

    private void check(JSONArray jsonArray, String testCaseName) {
        check(jsonArray, this.jsonArrayMap, testCaseName);
    }

    /**
     * 检查json串是否满足预期
     *
     * @param jsonArray json数组
     */
    private void check(JSONArray jsonArray,
                       Map<String, Object> jsonArrayMap,
                       String testCaseName,
                       int showCount) {
        this.logJsonArrayMap = jsonArrayMap;
        this.logJsonArray = jsonArray.toString();
        if (checkJson(jsonArray, jsonArrayMap, testCaseName)) {
            checkJsonArrayShowCount(jsonArray, showCount);
            for (Map.Entry<String, Object> entry : jsonArrayMap.entrySet()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    this.logJsonArray = "[" + (i + 1) + "]" + jsonArray.getJSONObject(i).toString();
                    try {
                        if (entry.getValue() != null) {
                            check(entry.getValue(),
                                    jsonArray.getJSONObject(i).getString(entry.getKey()),
                                    testCaseName +
                                            ": " + getJsonTestCase(jsonArray, entry));
                        } else {
                            String actual = "";
                            try {
                                actual = jsonArray.getJSONObject(i).getString(entry.getKey());
                            } catch (Exception e) {
                                actual = e.toString() + "|" + entry.getKey();
                            }
                            check(customCheckJsonArrayObject(jsonArray.get(i), i, entry.getKey(), jsonArray.size()),
                                    actual,
                                    testCaseName +
                                            ": " + getJsonTestCase(jsonArray, entry)
                            );
                        }
                    } catch (Exception e) {
                        errException(testCaseName +
                                ": " + getJsonTestCase(jsonArray, entry), e);
                    }

                }
            }
        }
    }

    /**
     * 检查是否要执行json检查
     *
     * @param json
     * @param map
     * @return
     */
    private boolean checkJson(JSON json, Map<String, Object> map, String testCaseName) {
        if (json == null || map == null) {
            errException(false, "", "[json || map] is null", testCaseName);
            return false;
        } else if (json instanceof JSONObject) {
            if (map.size() == 0 && !json.isEmpty()) {
                errException(false, "", "[jsonMap] is 0", testCaseName);
                return false;
            } else if (json.isEmpty() && map.size() != 0) {
                errException(false, "", "[JSONObject] is Empty", testCaseName);
                return false;
            }

        } else if (json instanceof JSONArray) {
            if (json.size() == 0 && map.size() != 0) {
                errException(false, "", "[jsonArray] is 0", testCaseName);
                return false;
            } else if (json.size() != 0 && map.size() == 0) {
                errException(false, "", "[jsonArrayMap] is 0", testCaseName);
                return false;
            }

        }
        return true;
    }

    private void errException(boolean b, String expected, String actual, String testCase) {
        try {
            if (!b) {
                throw new IllegalArgumentException(
                        "</br>\n预期结果:[" + expected + "] " +
                                "</br>\n实际结果:[" + actual + "] </br>");
            } else {
                addCaseResult(b);
            }
        } catch (Exception e) {
            errException(testCase, e);
        }
    }


    private void errException(boolean b, String testCase) {
        errException(b, this.object1, this.object2, testCase);
    }


    /**
     * 打印错误输出
     *
     * @param testCase
     * @param e
     */
    protected final void errException(String testCase, Exception e) {
        addCaseResult(false);
        Map<String, String> textInfoMap = new LinkedHashMap<>();
        if (e == null) e = new Exception();
        try {
            if (!this.isPrintLog) {
                if (!Test.isJarRun()) {
                    PrintStream stream = new PrintStream(System.err, true);
                    stream.println(("\n" + StringUtils.repeat("-", 200) + "\n"
                            + this.caseName + "[" + this.tag + "]"
                            + "\n{" + testCase + "}异常:\n" +
                            "request:" + RecordLogNetworkRequests.getRecordLogNetworkRequests().getRequests(this.tag,this.getClass()) + "\n" +
                            "JSON_OBJECT:" + this.logJsonObject +
                            "\n" + "JSONOBJECT_MAP:" + this.logJsonObjectMap + "\n" +
                            "JSON_ARRAYS:" +
                            this.logJsonArray +
                            "\n" + "JSON_ARRAYS_MAP:" + this.logJsonArrayMap));
                    e.printStackTrace(stream);
                }
                textInfoMap.put("title", JavaUtils.strFormatting(this.toString(), String.valueOf(this.tag)));
                textInfoMap.put("testCase", "{" + testCase + "}异常");
                textInfoMap.put("request", RecordLogNetworkRequests.getRecordLogNetworkRequests().getRequests(this.tag,this.getClass()));
                textInfoMap.put("jsonObject", this.logJsonObject.toString());
                textInfoMap.put("jsonObjectMap", this.logJsonObjectMap.toString());
                textInfoMap.put("jsonArrays", this.logJsonArray);
                textInfoMap.put("jsonArraysMap", this.logJsonArrayMap.toString());
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            e.addSuppressed(e1);
            textInfoMap.put(this.getClass().toString(), "errException");
        } finally {
            if (!this.isPrintLog) {
                LogUtils.outErrinfo(textInfoMap, e);
                this.isPrintLog = true;
            }
        }


    }

    /**
     * 用例执行结果
     *
     * @return
     */
    public String toString() {
        if (this.checkBoolean == null)
            return HtmlUtils.getColourFormatting(this.caseName + ": false [checkBooleanArrays=null]", "red");
        if (this.checkBoolean.length == 0)
            return HtmlUtils.getColourFormatting(this.caseName + ": false [checkBooleanArrays.length=0]", "red");
        for (boolean b : this.checkBoolean) {
            if (!b) return HtmlUtils.getColourFormatting(this.caseName + ": false", "red");
        }
        return HtmlUtils.getColourFormatting(this.caseName + ": true", "green");
    }

    /**
     * 用例执行结果
     *
     * @return
     */
    public boolean checkCaseResult() {
        if (this.checkBoolean == null) return false;
        if (this.checkBoolean.length == 0) return false;
        for (boolean b : this.checkBoolean) {
            if (!b) return false;
        }
        return true;
    }

    /**
     * 用于保存用例中的每一条case是否通过
     *
     * @param tf
     */
    private void addCaseResult(boolean tf) {
        this.checkBoolean =
                Arrays.copyOf(this.checkBoolean, this.checkBoolean.length + 1);
        this.checkBoolean[this.checkBoolean.length - 1] = tf;

    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void addJsonMap(String key, Object object) {
        this.jsonMap.put(key, object);
    }

    public void addJsonArrayMap(String key, Object object) {
        this.jsonArrayMap.put(key, object);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public Map<String, Object> getJsonArrayMap() {
        loadJsonArrayMap();
        return jsonArrayMap;
    }

    public Map<String, Object> getJsonMap() {
        loadJsonMap();
        return jsonMap;
    }

    public TestFrame setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        return this;
    }

    public TestFrame setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    public void addCaseName(Object text) {
        this.caseName += " " + text;
    }

    public double getTag() {
        return tag;
    }

    public TestFrame setTag(double tag) {
        this.tag = tag;
        return this;
    }

    public TestFrame setCaseName(Object caseNmae) {
        setCaseName(caseNmae, false);
        return this;
    }

    public TestFrame setCaseName(Object caseNmae, boolean coverage) {
        if (coverage) {
            this.caseName = JavaUtils.strFormatting(this.getClass().getName(), caseNmae.toString());
        } else {
            this.caseName += JavaUtils.strFormatting(caseNmae.toString());
        }
        return this;
    }
    public String getCaseName(){
        return this.caseName;
    }
    public int getShowCount() {
        return showCount;
    }

    public TestFrame setShowCount(int showCount) {
        this.showCount = showCount;
        return this;
    }


}

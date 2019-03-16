package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 书籍换一换
 */
public class ChangeBook extends RoofNewExpress {
    private TestFrame testFrame;

    ChangeBook(double number) {
        super();
        this.setTag(number);
    }

    ChangeBook(TestFrame testFrame, double number) {
        super();
        this.setTag(number);
        statr(testFrame, BoutiqueConfig.BOUTIQUE_CHANGE_BOOKS,
                "bdid=" + testFrame.getJsonObject().getInt("id")
                , "pageIndex", "data");
    }


    final void statr(TestFrame testFrame,
                     String path,
                     String query,
                     String IterativeParameter,
                     String getJSONArrayKey) {
        try {
            setTestFrame(testFrame);
            check(path,
                    query
                    , IterativeParameter, getJSONArrayKey);

        } catch (Exception e) {
            errException("解析", e);
        }
    }

    /**
     * 执行循环
     *
     * @param path               路径
     * @param query              参数
     * @param IterativeParameter 需要迭代的参数，自动拼接,不要添加&和=
     */
    private void check(String path, String query, String IterativeParameter, String getJSONArrayKey) {
        JSONObject jsonObject;
        if (IterativeParameter != null) {
            query = query.endsWith("&") ? query.substring(1) : query;
            IterativeParameter = IterativeParameter.startsWith("&") ?
                    IterativeParameter.substring(1) : IterativeParameter;
            IterativeParameter = IterativeParameter.endsWith("=") ?
                    IterativeParameter.substring(0, IterativeParameter.length() - 1) : IterativeParameter;
        }
        String repetition = null;//用于检查内容是否出现重复
        for (int i = 1; i < 50; i++) {
            this.setCaseName("for_" + i);
            jsonObject = JSONObject.fromObject(
                    AutomationUtils.doGet(path,
                            IterativeParameter != null ?
                                    query + "&" + IterativeParameter + "=" + i
                                    : query
                    )
            );
            this.setJsonObject(jsonObject);
            this.setJsonArray(jsonObject.getJSONArray(getJSONArrayKey));
            this.setShowCount(this.testFrame.getShowCount());
            this.stratCheck();
            if (repetition == null) {
                repetition = jsonObject.getString(getJSONArrayKey);
            } else {
                check(!repetition.equals(jsonObject.getString(getJSONArrayKey)),
                        "上一次的结果:" + repetition + " \n" +
                                "当前结果:" + jsonObject.getString(getJSONArrayKey),
                        "出现重复内容");
                repetition = jsonObject.getString(getJSONArrayKey);
            }


        }
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.putAll(this.testFrame.getJsonArrayMap());
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        jsonMap.put("info", "SUCCESS");
    }


    private void setTestFrame(TestFrame testFrame) throws NullPointerException {
        if (testFrame == null) throw new NullPointerException("testFrame");
        this.testFrame = testFrame;
    }
}

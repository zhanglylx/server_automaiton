package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 顶部banner
 */
public class RoofRoot extends TestFrame {
    private Pattern actionUrl;
    private int flag;
    private Pattern actionName;
    private int show;
    private Pattern name;
    public static final int FLAG = 10;


    public RoofRoot() {
        super();
    }

    public RoofRoot(JSONObject jsonObject,
                    int flag,
                    int show,
                    String name,
                    Pattern actionName,
                    Pattern actionUrl,
                    int showCount) {
        super(name + ":flag\"" + flag + "\"",
                jsonObject,
                jsonObject.getJSONArray("list"),
                showCount);
        this.actionUrl = actionUrl;
        this.flag = flag;
        this.actionName = actionName;
        this.show = show;
        this.name = Pattern.compile(name);

    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("adUrl", AutomationUtils.getCheckRules(
                AutomationUtils.STICK_SLIDESHOWS_AD_URL
        ));
        jsonArrayMap.put("adImgUrl", AutomationUtils.getCheckRules(
                AutomationUtils.AD_IMG_URL
        ));
        jsonArrayMap.put("id", AutomationUtils.getCheckRules(
                AutomationUtils.ID
        ));
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("id", AutomationUtils.getCheckRules(
                AutomationUtils.ID
        ));
        jsonMap.put("flag", this.flag);
        jsonMap.put("show", this.show);
        jsonMap.put("name", this.name);
        jsonMap.put("actionName", this.actionName);
        jsonMap.put("actionUrl", this.actionUrl);

    }


    @Override
    public void customCheck() {

    }

    public Pattern getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(Pattern actionUrl) {
        this.actionUrl = actionUrl;
    }

    public int getFlag() {
        return flag;
    }

    public RoofRoot setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    public Pattern getActionName() {
        return actionName;
    }

    public void setActionName(Pattern actionName) {
        this.actionName = actionName;
    }

    public int getShow() {
        return show;
    }

    public RoofRoot setShow(int show) {
        this.show = show;
        return this;
    }

    public Pattern getName() {
        return name;
    }

    public void setName(Pattern name) {
        this.name = name;
    }

}

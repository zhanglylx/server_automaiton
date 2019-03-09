package com.mfeia.book.server_automaiton.start_related;

import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

public class CheckVersion extends TestFrame {
    private int cnid;
    //是否是最新版本code
    private boolean newestVercode;

    CheckVersion(JSONObject jsonObject, int cnid, boolean newestVercode,int vercode) {
        super(jsonObject);
        this.cnid = cnid;
        this.newestVercode = newestVercode;
        if(this.newestVercode){
            setCaseName("检查接口更新提示:vercode:"+vercode+" 小于当前版本，服务端返回更新提示");
        }else {
            setCaseName("检查接口更新提示:vercode:"+vercode+" 大于或等于当前版本，服务端返回无更新");
        }
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        if(this.newestVercode){
            jsonMap.put("versionInfo", AutomationUtils.getCheckRules(AutomationUtils.TEXT));
            jsonMap.put("type", 1);
            jsonMap.put("url", Pattern.compile(".+/apk/" + this.cnid + "\\.apk.*"));
            jsonMap.put("isUpdate", 1);
        }else {
            jsonMap.put("isUpdate", 0);
        }



    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

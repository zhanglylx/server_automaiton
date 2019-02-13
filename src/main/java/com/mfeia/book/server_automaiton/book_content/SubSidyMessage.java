package com.mfeia.book.server_automaiton.book_content;

import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

public class SubSidyMessage extends TestFrame {
    public SubSidyMessage(JSONObject jsonObject) {
        super(jsonObject);
        check("您当前阅读的章节总价格已超过6元（费用已由我司补贴），" +
                        "特赠送您30天的全站免费畅读特权，祝您阅读愉快～",
                this.getJsonObject().getJSONObject("data").getJSONObject("privilege").getString("content")
                ,"检查privilege:content");
        check("亲爱的用户，本书为VIP图书，本书所有VIP章节费用已由我司为您承担，您可免费阅读本书。",
                this.getJsonObject().getJSONObject("data").getJSONObject("vipSubsidy").getString("content")
                ,"检查vipSubsidy:content");
        check("亲爱的用户，本书为VIP图书，您已阅读至VIP付费章节，" +
                        "后续阅读的所有章节费用已由我司为您承担，您可继续免费阅读本书。",
                this.getJsonObject().getJSONObject("data").getJSONObject("vipSubsidyMonth").getString("content")
                ,"检查vipSubsidyMonth:content");
        check("V章补贴声明",
                this.getJsonObject().getJSONObject("data").getJSONObject("vipSubsidy").getString("title")
                ,"检查vipSubsidy:title");
        check("V章补贴声明",
                this.getJsonObject().getJSONObject("data").getJSONObject("vipSubsidyMonth").getString("title")
                ,"检查vipSubsidyMonth:title");
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        return null;
    }
}

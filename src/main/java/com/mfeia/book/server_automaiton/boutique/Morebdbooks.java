package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 书籍更多
 */
public class Morebdbooks extends RoofNewExpress {
    private int id;
    private int pageNo;
    private Map<String, Object> map;
    public Morebdbooks(TestFrame roofFlagOne) {
        super();
        try {
            this.map = roofFlagOne.getJsonArrayMap();
            this.id = roofFlagOne.getJsonObject().getInt("id");
            this.setJsonObject(
                    JSONObject.fromObject(
                            AutoHttpUtils.doGet(
                                    BoutiqueConfig.BOUTIQUE_MOREBD_BOOKS,
                                    "bdId=" + id +
                                            "&pageNo=1")
                    )
            );
            this.setJsonArray(this.getJsonObject().getJSONArray("list"));
        } catch (Exception e) {
            this.errException("解析", e);
        }
        this.pageNo = 1;
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonMap) {
        if (this.getJsonArray() == null || this.getJsonArray().size() == 0) return;
        jsonMap.putAll(map);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("id", this.id);
        jsonMap.put("pageNo", this.pageNo);
    }

}

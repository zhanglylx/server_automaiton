package com.mfeia.book.server_automaiton.search;

import net.sf.json.JSONObject;
import server_automaiton_gather.TestFrame;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 联想词
 */
public class Searchassociationwords extends TestFrame {
    private String keyword;

    Searchassociationwords(JSONObject jsonObject, String keyword) {
        super("搜索联想词",jsonObject,jsonObject.getJSONArray("data"),1);
        this.keyword = keyword;
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("keyword",  this.keyword);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("id",null);
        jsonArrayMap.put("text",null);
        jsonArrayMap.put("type",null);

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        int type = jsonObject.getInt("type");
        if("type".equals(key))return Pattern.compile("[1023]");
        if("id".equals(key)){
            if(type == 1){
                return AutomationUtils.getCheckRules(AutomationUtils.BOOK_ID);
            }else if(type == 2){
                return Pattern.compile(".*"+this.keyword+".*");
            }else{
                return Pattern.compile(".*");
            }
        }
        if("text".equals(key)){
            return Pattern.compile(".*"+this.keyword+".*");
        }
        return null;
    }

    @Override
    public void customCheck() {

    }
}

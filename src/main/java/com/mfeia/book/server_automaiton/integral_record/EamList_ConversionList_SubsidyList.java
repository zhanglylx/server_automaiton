package com.mfeia.book.server_automaiton.integral_record;

import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 积分获取记录
 */
public class EamList_ConversionList_SubsidyList extends TestFrame {
    //判断具体是哪一个记录，因为图书记录中的key(List)是大写，其他是小写并且还多了一个参数dayIntegral
    private boolean eamList;

    EamList_ConversionList_SubsidyList(JSONObject jsonObject, boolean eamList, String EamList_ConversionList_SubsidyList) {
        super(jsonObject);
        setCaseName(EamList_ConversionList_SubsidyList);
        this.eamList = eamList;

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("result", 1);
        jsonMap.put("isred", 0);
        jsonMap.put("nextPage", 2);
        jsonMap.put("totalRecord", 0);
        jsonMap.put("totalPage", 0);

        if (this.eamList) {
            jsonMap.put("dayIntegral", 0);
            jsonMap.put("dataList", "[]");
        }else{
            jsonMap.put("datalist", "[]");
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

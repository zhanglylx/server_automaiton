package com.mfeia.book.server_automaiton.book_catalog;

import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.Book;
import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 检查目录中id不重复
 */
public class Catalog extends TestFrame {
    private List<Long> list = new ArrayList<>();

    public Catalog(JSONObject jsonObject, JSONArray jsonArray, double number, Book books) {
        super(jsonObject, jsonArray);
        this.setCaseName("目录:" + books.getBookId() + ":" + number);
    }


    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        //因目录结构复杂，直接由checkJsonObjec处理
        jsonArrayMap.put("id", null);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("error_code", 0);
    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        checkRepeatSections(JSONObject.fromObject(object).getLong("id"),
                index, "卷");
        check(AutomationUtils.getCheckRules(AutomationUtils.BOOK_NAME),
                JSONObject.fromObject(object).getString("name"),
                "检查卷名称");
        JSONArray jsonArray = JSONObject.fromObject(object).getJSONArray("bookChapters");
        for (int i = 0; i < jsonArray.size(); i++) {
            check(AutomationUtils.getCheckRules(AutomationUtils.BOOK_NAME),
                    jsonArray.getJSONObject(i).getString("name"),
                    "检查bookChapters名称");
            checkRepeatSections(jsonArray.getJSONObject(i).getLong("id"),
                    index, "bookChapters:" + (i+1) );

        }

        return true;
    }

    /**
     * 检查重复章节
     */
    private void checkRepeatSections(long id, int index, String caseName) {
        if (this.list.contains(id)) {
            check(false, caseName + "_重复章节:" + id + " index:" + index
                    , "检查重复章节id");
        } else {
            this.list.add(id);
        }
    }

}

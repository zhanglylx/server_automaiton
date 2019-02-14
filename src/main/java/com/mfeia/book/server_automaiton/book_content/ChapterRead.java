package com.mfeia.book.server_automaiton.book_content;


import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.Book;
import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 读书获取当前和上一章
 * 使用第一次进入阅读页
 */
public class ChapterRead extends TestFrame {

    public ChapterRead(JSONObject jsonObject, Book books, double number) {
        super(String.valueOf(books.getBookId()) + ":" + number,
                jsonObject,
                jsonObject.getJSONArray("chapterList"),
                2);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("id",
                AutomationUtils.getCheckRules(
                        AutomationUtils.BOOK_ID
                ));
        jsonArrayMap.put("name",
                AutomationUtils.getCheckRules(
                        AutomationUtils.BOOK_NAME
                ));
        jsonArrayMap.put("flag", null);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("error_code", 0);
    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        if (index == 0) {
            check("curr".equals(jsonObject.getString("flag")),
                    "预期结果:curr 实际结果:" + jsonObject.getString("flag"),
                    "检查当前章节"
            );
        } else {
            check("next".equals(jsonObject.getString("flag")),
                    "预期结果:next 实际结果:" + jsonObject.getString("flag"),
                    "检查下一章节"
            );
        }
        return true;
    }
}

package com.mfeia.book.server_automaiton.book_content;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import com.mfeia.book.server_automaiton.Book;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 尾页推荐
 */
public class Recommend extends TestFrame {
    public Recommend(JSONObject jsonObject, Book books, double number) {
        super("尾页推荐:" + books.getBookId(), jsonObject,
                jsonObject.getJSONObject("data").getJSONArray("otherBookInfo"),
                1);
        JSONObject thisBookInfoJSONObject = jsonObject.getJSONObject("data").getJSONObject("thisBookInfo");
        this.setTag(number);
        check(books.equals(thisBookInfoJSONObject),
                "预期结果:" + books + " </br>\n实际结果:" + thisBookInfoJSONObject
                , "检查thisBookInfo:" + books.getBookId()
        );
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.putAll(AutomationUtils.getCheckBookAll());
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
        jsonMap.put("info", "SUCCESS");
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return AutomationUtils.getCheckRules(AutomationUtils.BOOK_COVER,
                JSONObject.fromObject(object).getString(AutomationUtils.BOOK_ID)
        );
    }

    @Override
    public void customCheck() {

    }
}

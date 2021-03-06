package com.mfeia.book.server_automaiton.detail_page;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.mfeia.book.server_automaiton.Book;
import server_automaiton_gather.TestFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 书籍详情页
 */
public class Detail extends TestFrame {

    public Detail(JSONObject jsonObject, double number, final Book books) {
        super("详情页:" + books, jsonObject.getJSONObject("data"),
                jsonObject.getJSONObject("data").getJSONArray("everyoneLookBookList"),
                1);
        this.setTag(number);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        check(jsonObject, map, "检查详情页code");
        jsonObject = this.getJsonObject().getJSONObject("bookVo");
        map.clear();
        map.putAll(books.getCheckMap());

        map.put("updateDate", Pattern.compile(".+"));
        check(jsonObject, map, "检查详情页:bookVo");
        /*
        作者其他书籍
         */
        JSONArray jsonArray = this.getJsonObject().getJSONArray("otherBookList");

        for (int i = 0; i < jsonArray.size(); i++) {
            final String bookid = jsonArray.getJSONObject(i).getString("bookId");
            TestFrame testFrame = new TestFrame() {
                @Override
                public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

                }

                @Override
                public void settingJsonMap(Map<String, Object> jsonMap) {
                    jsonMap.putAll(
                            AutomationUtils.getCheckBookAll(
                                    bookid));
                    jsonMap.put("authorName", books.getAuthorName());
                }

                @Override
                public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
                    return null;
                }

                @Override
                public void customCheck() {

                }
            }.setJsonObject(jsonArray.getJSONObject(i)).setCaseName("检查otherBookList").setTag(number);

            this.check(testFrame.stratCheck().checkCaseResult(),
                    "使用匿名类检查otherBookList,错误信息单独打印,此打印提示为匿名类存在检查错误",
                    "检查otherBookList");
        }
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
        jsonArrayMap.put("authorName", AutomationUtils.getCheckRules(
                AutomationUtils.BOOK_AUTHOR_NAME
        ));
        jsonArrayMap.put("bookId", AutomationUtils.getCheckRules(
                AutomationUtils.BOOK_ID
        ));
        jsonArrayMap.put("bookImg", null);
    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("vcontent", "亲爱的用户，本书为VIP图书，本书所有VIP章节费用已由我司为您承担，您可免费阅读本书。");
        jsonMap.put("vtitle", "V章补贴声明");

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return AutomationUtils.getCheckRules(
                AutomationUtils.BOOK_COVER, ((JSONObject) object).getString("bookId")
        );
    }

    @Override
    public void customCheck() {

    }
}

package com.mfeia.book.server_automaiton.jenkins_build;

import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import com.mfeia.book.server_automaiton.Book;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 获取图书信息
 */
public class PackageBookInfo extends TestFrame {

    private Book book;
    private long mFirstChapter;
    public PackageBookInfo(JSONObject jsonObject, Book book) {
        super(jsonObject.getJSONObject("data"));
        setCaseName(book);
        this.book = book;
        this.mFirstChapter = this.getJsonObject().getLong("mFirstChapter");
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        //在jenkins打包时，因涉及推广的一些限制，书籍名称会进行更改
//        jsonMap.put("mBookName", this.book.getBookName());
        jsonMap.put("mImg", this.book.getBookImg());
        jsonMap.put("mBookId", this.book.getBookId());
        jsonMap.put("mAuthorName", this.book.getAuthorName());
        jsonMap.put("mFirstChapter", AutomationUtils.getCheckRules(AutomationUtils.ID));
        jsonMap.put("mLastChapter", AutomationUtils.getCheckRules(AutomationUtils.ID));
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }

    public Book getBook() {
        return book;
    }

    public long getmFirstChapter() {
        return mFirstChapter;
    }
}

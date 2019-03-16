package com.mfeia.book.server_automaiton.book_catalog;

import com.mfeia.book.server_automaiton.Book;
import server_automaiton_gather.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 目录刷新
 */
public class IsChapterUpdate extends TestFrame {
    public IsChapterUpdate(JSONObject jsonObject, Book books,double number){
        super(jsonObject);
        this.setCaseName("目录刷新:"+books.getBookId()+":"+number);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code",0);
        if(this.getJsonObject().getInt("isUpdate")==0){
            jsonMap.put("isUpdate",0);
        }else{
            jsonMap.put("isUpdate",1);
            jsonMap.put("updatetime", Pattern.compile("\\d{13}"));
        }

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}

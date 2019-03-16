package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PaiHangStackRoomConfig {
    static final String PAI_HANG_PHINDEXYS = "pai.hang.pindexys";
    static final String PAI_HANG_NEWRANKLIST = "pai.hang,NewRankList";
    static final String STACK_ROOM_FINDEXYS = "stack.room.findexys";
    static final String STACK_ROOM_CATELISTNEW = "stack.room.cateListNew";

    static List<String> analysisActionUrl(String url) throws Exception {
        if (url == null) throw new NullPointerException("analysisActionUrl NULL");
        if (url.isEmpty()) throw new IllegalArgumentException("analysisActionUrl isEmpty");
        URIBuilder builder = new URIBuilder(url);
        Pattern CRLF = Pattern.compile("\\d+");
        List<String> list = new ArrayList<>();
        for (NameValuePair nameValuePair : builder.getQueryParams()) {
            Matcher m = CRLF.matcher(nameValuePair.getValue());
            if (m.find()) {
                list.add(m.group());
            }
        }
        return list;
    }
}

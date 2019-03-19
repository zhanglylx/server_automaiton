package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PaiHangStackRoomConfig {
    static final String PAI_HANG_PHINDEXYS = "pai.hang.pindexys";
    static final String PAI_HANG_NEWRANKLIST = "pai.hang,NewRankList";
    static final String STACK_ROOM_FINDEXYS = "stack.room.findexys";
    static final String STACK_ROOM_CATELISTNEW = "stack.room.cateListNew";
    static final String STATCK_ROOM_MZ_CATELOGUE = "statc.room.mz.catelogue";

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

    static Map<String, Object> getCheckItemMap(String actionUrlIdName, boolean isCatelogue) {
        Map<String, Object> itemChcekMap = new HashMap<>();
        itemChcekMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
        itemChcekMap.put("tagName", 0);
        itemChcekMap.put("name", Pattern.compile(".{3,4}"));
        itemChcekMap.put("actionUrl", AutomationUtils.getCheckRules(actionUrlIdName));
        if (isCatelogue) itemChcekMap.put("img", AutomationUtils.getCheckRules(AutomationUtils.SHU_KU_IMG));
        return itemChcekMap;
    }

    static Map<String, Object> getCheckThirdCateList() {
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("id", AutomationUtils.getCheckRules(AutomationUtils.ID));
        checkMap.put("name", Pattern.compile(".{2,6}"));
        return checkMap;
    }
}

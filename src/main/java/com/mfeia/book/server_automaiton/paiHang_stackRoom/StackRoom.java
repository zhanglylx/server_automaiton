package com.mfeia.book.server_automaiton.paiHang_stackRoom;

import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

/**
 * 书库
 */

public class StackRoom extends Phindexys {
    StackRoom(JSONObject jsonObject) {
        super(jsonObject, AutomationUtils.NEW_CATE_LIST_FIND);
    }

}

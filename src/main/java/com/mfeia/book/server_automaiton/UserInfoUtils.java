package com.mfeia.book.server_automaiton;

import com.mfeia.book.server_automaiton.background_interface.BackgroundInterfaceConfig;
import com.mfeia.book.server_automaiton.user_related.MySidebar;
import net.sf.json.JSONObject;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserInfoUtils {
    private static String historyUserId;
    public static final String USER_MY_SIDEBAR = "user.related.mySidebar";

    static {
        resetHistoryUserId();
        resetNewUserId();
    }


    public static String getHistoryUserId() {
        resetHistoryUserId();
        return historyUserId;
    }

    public static void resetHistoryUserId() {
        try {
            historyUserId = getUserJSONbject(AutomationUtils.getServerAutomaitonProperties(AutomationUtils.TEL)).
                    getJSONObject("data").
                    getString("id");
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(UserInfoUtils.class, "初始化userId", e)
            );
            historyUserId = null;
        }
    }

    private static String resetNewUserId() {
        String newUd = null;
        try {
            newUd = new MySidebar().getNewUserId(true);
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(UserInfoUtils.class, "获取新用户", e)
            );
        }
        return newUd;
    }

    public static String getNewUserId() {
        return resetNewUserId();
    }

    public static JSONObject getUserJSONbject(String tel) {
        Map<String, String> map = new HashMap<>();
        map.put("tel", tel);
        map.put("mac",UUID.randomUUID().toString());
        String str =
                AutoHttpUtils.doPost(BackgroundInterfaceConfig.BACKGROUND_USER, map);
        return JSONObject.fromObject(str);
    }

    public static String getNewMac(){
        return  "ikanshu-test-"+UUID.randomUUID().toString()+System.currentTimeMillis();
    }
}

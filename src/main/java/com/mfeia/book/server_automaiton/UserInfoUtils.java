package com.mfeia.book.server_automaiton;

import com.mfeia.book.server_automaiton.background_interface.TestCasesBackgroundInterface;
import com.mfeia.book.server_automaiton.user_related.MySidebar;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoUtils {
    private static String historyUserId;
    public static final String USER_MY_SIDEBAR = "user.related.mySidebar";

    static {
        resetHistoryUserId();
        resetNewUserId();
    }


    public static String getHistoryUserId() {
        return historyUserId;
    }

    public static void resetHistoryUserId() {
        try {
            historyUserId = TestCasesBackgroundInterface.
                    getUserJSONbject().
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
}

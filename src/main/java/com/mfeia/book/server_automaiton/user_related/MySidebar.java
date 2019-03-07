package com.mfeia.book.server_automaiton.user_related;

import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.TestFrame;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 侧边栏
 */
public class MySidebar extends TestFrame {
    private Map<String, String> heades;
    private String newUserId = null;

    public MySidebar() {
        this(-1d);
    }

    public MySidebar(double number) {
        this.setTag(number);
        this.heades = new HashMap<>(AutomationUtils.getMapHeaders());
    }

    public TestFrame stratCheck() {
        checkGetNewUser();
        checkGetHistoryUser();
        return this;
    }

    /**
     * 检查获取新用户
     */
    private void checkGetNewUser() {
        this.heades.put("mac", UUID.randomUUID().toString());
        long userId1 = getJSONObjcetUserId();
        this.heades.put("mac", UUID.randomUUID().toString());
        long userId2 = getJSONObjcetUserId();
        if (userId2 <= userId1) {
            check(false,
                    "第一次获取userId:" + userId1 + " 第二次获取userId:" + userId2,
                    "检查获取新用户");
        } else {
            this.newUserId = String.valueOf(userId2);
        }

    }


    /**
     * 检查获取历史用户
     */
    private void checkGetHistoryUser() {
        this.heades.put("mac", UUID.randomUUID().toString());
        this.heades.put("uid", this.newUserId);
        long userId1 = getJSONObjcetUserId();
        check(userId1 == Long.parseLong(this.newUserId),
                "获取的userId:" + userId1 + " 传入的userId:" + this.newUserId,
                "检查获取历史用户id");
    }


    private long getJSONObjcetUserId() {
        return getMySidebarJSONObject().getJSONObject("user").getLong("id");
    }


    private JSONObject getMySidebarJSONObject() {
        return JSONObject.fromObject(
                AutomationUtils.doGet(
                        UserInfoUtils.USER_MY_SIDEBAR, "", this.heades));

    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {

    }

    @Override
    public Object checkJsonObjec(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }

    public String getNewUserId(boolean reset) {
        if (this.newUserId == null || reset) checkGetNewUser();
        return newUserId;
    }

    public String getNewUserId() {

        return getNewUserId(false);
    }
}

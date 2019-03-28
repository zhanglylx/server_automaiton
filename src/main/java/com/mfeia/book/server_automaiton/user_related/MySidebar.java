package com.mfeia.book.server_automaiton.user_related;

import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.TestFrame;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 侧边栏
 */
public class MySidebar extends TestFrame {
    private String newUserId = null;

    public MySidebar() {
        this(-1d);
    }

    public MySidebar(double number) {
        this.setTag(number);
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
        Map<String, String> heades = new HashMap<>(AutoHttpUtils.getMapHeaders());
        heades.put("mac", UserInfoUtils.getNewMac());
        long userId1 = getJSONObjcetUserId(heades);
        heades.put("mac", UserInfoUtils.getNewMac());
        long userId2 = getJSONObjcetUserId(heades);
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
        Map<String, String> heades = new HashMap<>(AutoHttpUtils.getMapHeaders());
        heades.put("mac", UUID.randomUUID().toString());
        heades.put("uid", this.newUserId);
        long userId1 = getJSONObjcetUserId(heades);
        check(userId1 == Long.parseLong(this.newUserId),
                "获取的userId:" + userId1 + " 传入的userId:" + this.newUserId,
                "检查获取历史用户id");
    }


    private long getJSONObjcetUserId(Map<String, String> heades) {
        return getMySidebarJSONObject(heades).getJSONObject("user").getLong("id");
    }


    private JSONObject getMySidebarJSONObject(Map<String, String> heades) {
        return JSONObject.fromObject(
                AutoHttpUtils.doGet(
                        UserInfoUtils.USER_MY_SIDEBAR, "", heades,this.getTag()));

    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {

    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
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

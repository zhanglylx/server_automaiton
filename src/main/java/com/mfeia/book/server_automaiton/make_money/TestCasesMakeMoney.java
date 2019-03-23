package com.mfeia.book.server_automaiton.make_money;

import com.mfeia.book.server_automaiton.*;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.TestFrame;

import java.util.Map;

/**
 * 注意:请确认后台已配置时长任务
 */
public class TestCasesMakeMoney implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws Exception {
        String newUserId = UserInfoUtils.getNewUserId();
        //获取赚钱页
        ListEarnIntegralByHd listEarnIntegralByHd = null;
        try {
            listEarnIntegralByHd = getListEarnIntegralByHd(newUserId);
        } catch (Exception e) {
            performInspection.addtestFrameList(
                    new ErrException(TestCasesMakeMoney.class,
                            "如果出现此条报错信息，请先确认后台是否配置时长任务",
                            e), number);
            return;
        }
        performInspection.addtestFrameList(listEarnIntegralByHd, number);

        //领取任务taskId
        JSONObject jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(MakeMoneyConfig.MAKE_MONEY_RECEIVE_TASK_OR_REWARD,
                        "taskid=" + listEarnIntegralByHd.getTaskId() +
                                "&uid=" + newUserId +
                                "&status=0" +
                                "&type=0" +
                                "&myTaskId=" + listEarnIntegralByHd.getMyTaskId())
        );
        ReceiveTaskOrReward receiveTaskOrReward = new ReceiveTaskOrReward(
                jsonObject);
        performInspection.addtestFrameList(receiveTaskOrReward, number);


        //通知服务端激活任务
        jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(MakeMoneyConfig.MAKE_MONEY_TASK_STATUS_UPDATE,
                        "id=" + receiveTaskOrReward.getMyTaskId() +
                                "&uid=" + newUserId +
                                "&status=1")
        );
        performInspection.addtestFrameList(new TaskStatusUpdate(jsonObject), number);

        //通知服务端完成任务
        jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(MakeMoneyConfig.MAKE_MONEY_TASK_STATUS_UPDATE,
                        "id=" + receiveTaskOrReward.getMyTaskId() +
                                "&uid=" + newUserId +
                                "&status=2")
        );
        performInspection.addtestFrameList(new TaskStatusUpdate(jsonObject), number);


        //领取任务奖励
        jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(MakeMoneyConfig.MAKE_MONEY_RECEIVE_TASK_OR_REWARD,
                        "taskid=" + listEarnIntegralByHd.getTaskId() +
                                "&uid=" + newUserId +
                                "&status=2" +
                                "&type=2" +
                                "&myTaskId=" + receiveTaskOrReward.getMyTaskId())
        );

        ListEarnIntegralByHd finalListEarnIntegralByHd = listEarnIntegralByHd;
        performInspection.addtestFrameList(new TestFrame(jsonObject) {

            @Override
            public void settingJsonMap(Map<String, Object> jsonMap) {
                jsonMap.put("code", 0);
                jsonMap.put("integral", finalListEarnIntegralByHd.getRewardIntegral());
                jsonMap.put("status", 4);
            }

            @Override
            public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

            }

            @Override
            public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
                return null;
            }

            @Override
            public void customCheck() {

            }
        }, number);



        /*
        以下检查用户做完任务后积分是否加入到账户中
         */
        performInspection.addtestFrameList(new TestFrame() {
            @Override
            public void settingJsonMap(Map<String, Object> jsonMap) {

            }

            @Override
            public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

            }

            @Override
            public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
                return null;
            }

            @Override
            public void customCheck() {
                check(finalListEarnIntegralByHd.getRewardIntegral(),
                        getUserIntegral(newUserId),
                        "检查时长任务完成后积分是否增加到账户中,用户ID:" + newUserId);
            }
        }, number);
    }

    private int getUserIntegral(String newUserId) {
        return getListEarnIntegralByHdJSONObject(newUserId).getInt("integral");
    }

    private JSONObject getListEarnIntegralByHdJSONObject(String newUserId) {
        return JSONObject.fromObject(
                AutoHttpUtils.doGet(MakeMoneyConfig.MAKE_MONEY_LIST_EARN_INTERGRAL_BY_HD,
                        "cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID) +
                                "&uid=" + newUserId +
                                "&updatetime=0")
        );
    }

    private ListEarnIntegralByHd getListEarnIntegralByHd(String newUserId) {
        return new ListEarnIntegralByHd(
                getListEarnIntegralByHdJSONObject(newUserId).getJSONArray("listObj").getJSONObject(0)
                        .getJSONArray("listTask").getJSONObject(0));

    }

}

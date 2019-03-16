package com.mfeia.book.server_automaiton.background_interface;

import ZLYUtils.DoubleOperation;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;
import org.apache.http.client.utils.URIBuilder;

import java.util.HashMap;
import java.util.Map;

public class TestCasesBackgroundInterface implements AddTestCases {

    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
        /**
         * 因为涉及到此接口会影响线上，服务端没有对参数进行检查，所有的参数都会插入到数据库中，所以线上运行时此脚本关闭
         */
        //        number = DoubleOperation.add(number, 0.01);
//        addDrcallBack(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addFinger(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addWeChatPayCallback(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addCallback(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addTtLogin(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addTaskSynchro(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addDuibaCredut(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addDuibaCreditConfirm(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addDuibaCreditConsume(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addVipMessage(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addUser(performInspection, number);
        number = DoubleOperation.add(number, 0.01);
        addOrderInfo(performInspection, number);
    }

    private static void addDrcallBack(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("hashid", "1");
        uriBuilder.addParameter("userid", "3");
        uriBuilder.addParameter("appid", "2");
        uriBuilder.addParameter("adname", "4");
        uriBuilder.addParameter("mac", "5");
        uriBuilder.addParameter("deviceid", "6");
        uriBuilder.addParameter("source", "7");
        uriBuilder.addParameter("point", "8");
        uriBuilder.addParameter("time", "9");
        uriBuilder.addParameter("checksum", "10");
        JSONObject jsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(BackgroundInterfaceConfig.BACKGROUND_INTERFACE, uriBuilder.build().getQuery())
        );

        performInspection.addtestFrameList(new DrCallBack(jsonObject), number);
    }


    private static void addFinger(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("mid", "1");
        uriBuilder.addParameter("account", "2");
        uriBuilder.addParameter("tid", "3");
        uriBuilder.addParameter("tname", "4");
        uriBuilder.addParameter("integral", "5");
        uriBuilder.addParameter("ua", "6");
        uriBuilder.addParameter("ifv", "7");
        uriBuilder.addParameter("mac", "8");
        uriBuilder.addParameter("udid", "9");
        uriBuilder.addParameter("order", "10");
        uriBuilder.addParameter("sign", "11");
        JSONObject jsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(BackgroundInterfaceConfig.BACKGROUND_FINGER, uriBuilder.build().getQuery())
        );
        performInspection.addtestFrameList(new Finger(jsonObject), number);
    }


    private static void addWeChatPayCallback(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("orderid", "1");
        uriBuilder.addParameter("openid", "2");
        uriBuilder.addParameter("transactionid", "3");
        String weChatPayCallback =
                AutomationUtils.doGet(
                        BackgroundInterfaceConfig.BACKGROUND_WECHATPAYCALLBACK, uriBuilder.build().getQuery());
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("message", weChatPayCallback);
        performInspection.addtestFrameList(new WeChatPayCallback(jsonObject), number);

    }

    /**
     * 松果积分墙
     *
     * @throws Exception
     */
    private static void addCallback(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("appid", "1");
        uriBuilder.addParameter("userid", "2");
        uriBuilder.addParameter("actid", "3");
        uriBuilder.addParameter("offer", "4");
        uriBuilder.addParameter("desc", "5");
        uriBuilder.addParameter("Signature", "6");
        uriBuilder.addParameter("t", "7");
        String str =
                AutomationUtils.doGet(BackgroundInterfaceConfig.BACKGROUND_CALLBACK, uriBuilder.build().getQuery());
        performInspection.addtestFrameList(new Callback(new JSONObject().accumulate("message", str)), number);
    }

    /**
     * QQ登录回调
     *
     * @throws Exception
     */
    private static void addTtLogin(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("code", "1");
        uriBuilder.addParameter("state", "2");
        uriBuilder.addParameter("version", "3");
        String str =
                AutomationUtils.doGet(BackgroundInterfaceConfig.BACKGROUND_TTLOGIN, uriBuilder.build().getQuery());
        performInspection.addtestFrameList(new TtLogin(new JSONObject().accumulate("message", str)), number);
    }


    /**
     * 兑吧请求的任务同步接口
     *
     * @throws Exception
     */
    private static void addTaskSynchro(PerformInspection performInspection, double number) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("uid", "1");
        map.put("taskdata", "2");
        JSONObject jsonObject = JSONObject.fromObject(
                AutomationUtils.doPost(BackgroundInterfaceConfig.BACKGROUND_TASK_SYNCHRO, map));
        performInspection.addtestFrameList(new TaskSynchro(jsonObject), number);
    }


    /**
     * 兑吧增加用户积分
     *
     * @throws Exception
     */
    private static void addDuibaCredut(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("orderNum", "1");
        uriBuilder.addParameter("appKey", "2");
        uriBuilder.addParameter("timestamp", "3");
        uriBuilder.addParameter("uid", "3");
        uriBuilder.addParameter("credits", "3");
        uriBuilder.addParameter("description", "3");
        uriBuilder.addParameter("type", "3");
        uriBuilder.addParameter("ip", "3");
        JSONObject jsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(
                        BackgroundInterfaceConfig.BACKGROUND_DUI_BA_CREADUTADD,
                        uriBuilder.build().getQuery())
        );
        performInspection.addtestFrameList(new DuibaCreditAdd(jsonObject), number);
    }

    /**
     * 兑吧兑换结果通知接口
     *
     * @throws Exception
     */
    private static void addDuibaCreditConfirm(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("appKey", "1");
        uriBuilder.addParameter("timestamp", "2");
        uriBuilder.addParameter("success", "3");
        uriBuilder.addParameter("errorMessage", "3");
        uriBuilder.addParameter("bizId", "3");
        uriBuilder.addParameter("uid", "3");
        uriBuilder.addParameter("orderNum", "3");
        String str =
                AutomationUtils.doGet(BackgroundInterfaceConfig.BACKGROUND_DUI_BA_CREDIT_CONFIRM, uriBuilder.build().getQuery());
        performInspection.addtestFrameList(new DuibaCreditConfirm(new JSONObject().accumulate("message", str)), number);
    }


    /**
     * 兑吧扣除用户积分
     *
     * @throws Exception
     */
    private static void addDuibaCreditConsume(PerformInspection performInspection, double number) throws Exception {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.addParameter("credits", "1");
        uriBuilder.addParameter("uid", "2");
        uriBuilder.addParameter("appKey", "3");
        uriBuilder.addParameter("timestamp", "3");
        uriBuilder.addParameter("description", "3");
        uriBuilder.addParameter("orderNum", "3");
        uriBuilder.addParameter("type", "3");
        uriBuilder.addParameter("facePrice", "3");
        uriBuilder.addParameter("actualPrice", "3");
        uriBuilder.addParameter("itemCode", "3");
        uriBuilder.addParameter("phone", "3");
        uriBuilder.addParameter("qq", "3");
        uriBuilder.addParameter("waitAudit", "3");
        uriBuilder.addParameter("ip", "3");
        uriBuilder.addParameter("params", "3");
        String str =
                AutomationUtils.doGet(BackgroundInterfaceConfig.BACKGROUND_DUI_BA_CREDIT_CONSUME, uriBuilder.build().getQuery());
        performInspection.addtestFrameList(new DuibaCreditConsume(JSONObject.fromObject(str)), number);
    }


    /**
     * 同步小程序vip 信息
     *
     * @throws Exception
     */
    private static void addVipMessage(PerformInspection performInspection, double number) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "1");
        map.put("orderId", "2");
        map.put("openId", "3");
        map.put("endTime", "4");
        map.put("cnId", "5");
        map.put("oldPrice", "5");
        map.put("price", "6");
        String str =
                AutomationUtils.doPost(BackgroundInterfaceConfig.BACKGROUND_VIPMESSAGE, map);
        performInspection.addtestFrameList(new VipMessage(JSONObject.fromObject(str)), number);
    }


    /**
     * 小程序获取免电用户信息
     * 使用配置中的手机号
     * @throws Exception
     */
    private static void addUser(PerformInspection performInspection, double number) throws Exception {
        String tel = AutomationUtils.getServerAutomaitonProperties(AutomationUtils.TEL);
        performInspection.addtestFrameList(new AppletUser(UserInfoUtils.getUserJSONbject(tel), tel), number);
    }



    /**
     * 小程序获取免电用户VIP信息
     *
     * @throws Exception
     */
    private static void addOrderInfo(PerformInspection performInspection, double number) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserInfoUtils.getNewUserId());
        String str =
                AutomationUtils.doPost(BackgroundInterfaceConfig.BACKGROUND_ORDER_INFO, map);
        performInspection.addtestFrameList(new AppletOrderInfo(JSONObject.fromObject(str)), number);
    }


}

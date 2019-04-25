package com.mfeia.book.server_automaiton.start_related;

import com.mfeia.book.server_automaiton.UserInfoUtils;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestCasesStartRelated implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws Exception {
        int vercode = Integer.parseInt(AutomationUtils.getServerAutomaitonProperties(AutomationUtils.VERCODE));
        int cnid = Integer.parseInt(AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID));
        JSONObject noUpdateJsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(StartRelatedConfig.START_RELATED_CHECK_VERSION,
                        "cnid=" + cnid +
                                "&vercode=" + (vercode),number
                )
        );

        performInspection.addtestFrameList(new CheckVersion(noUpdateJsonObject, cnid, false,vercode), number);
        vercode--;
        JSONObject updateJsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(StartRelatedConfig.START_RELATED_CHECK_VERSION,
                        "cnid=" + cnid +
                                "&vercode=" + (vercode),number
                )
        );
        performInspection.addtestFrameList(new CheckVersion(updateJsonObject, cnid, true, vercode), number);


        Map<String,String> headers = new HashMap<>(AutoHttpUtils.getMapHeaders());
        String newUid=UserInfoUtils.getNewUserId();
        headers.put("uid", newUid);
        JSONObject ginfoJson=JSONObject.fromObject(
                AutoHttpUtils.doGet(StartRelatedConfig.START_RELATED_GTINFO
                        ,"jgcid="+System.currentTimeMillis()
                ,headers,number)
        );
        performInspection.addtestFrameList(new GtInfo(ginfoJson),number);

        JSONObject newUserIgnoreChannelJson=JSONObject.fromObject(
                AutoHttpUtils.doGet(StartRelatedConfig.START_RELATED_NEW_USER_IGNORE_CHANNEL
                        ,""
                        ,headers,number)
        );
        performInspection.addtestFrameList(new NewUserIgnoreChannel(newUserIgnoreChannelJson,newUid,true),number);
        newUid="-1";
        headers.put("uid", newUid);
        newUserIgnoreChannelJson=JSONObject.fromObject(
                AutoHttpUtils.doGet(StartRelatedConfig.START_RELATED_NEW_USER_IGNORE_CHANNEL
                        ,""
                        ,headers,number)
        );
        performInspection.addtestFrameList(new NewUserIgnoreChannel(newUserIgnoreChannelJson,newUid,false),number);

    }
}

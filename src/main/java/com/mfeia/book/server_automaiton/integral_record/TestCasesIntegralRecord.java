package com.mfeia.book.server_automaiton.integral_record;

import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分记录
 */
public class TestCasesIntegralRecord implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws Exception {
        Map<String, String> headers = new HashMap<>(AutoHttpUtils.getMapHeaders());
        headers.put("uid", UserInfoUtils.getNewUserId());
        JSONObject jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(IntegralRecordConfig.INTEGRAL_RECORD_EARNLIST, "curpage=1", headers,number,EamList_ConversionList_SubsidyList.class)
        );
        performInspection.addtestFrameList(new EamList_ConversionList_SubsidyList(jsonObject,true,IntegralRecordConfig.INTEGRAL_RECORD_EARNLIST),number);
        jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(IntegralRecordConfig.INTEGRAL_RECORD_COVERSIONLIST, "curpage=1", headers,number,EamList_ConversionList_SubsidyList.class)
        );
        performInspection.addtestFrameList(new EamList_ConversionList_SubsidyList(jsonObject,false,IntegralRecordConfig.INTEGRAL_RECORD_COVERSIONLIST),number);
        jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doGet(IntegralRecordConfig.INTEGRAL_RECORD_SUBSIDYLIST, "curpage=1", headers,number,EamList_ConversionList_SubsidyList.class)
        );
        performInspection.addtestFrameList(new EamList_ConversionList_SubsidyList(jsonObject,false,IntegralRecordConfig.INTEGRAL_RECORD_SUBSIDYLIST),number);
    }
}

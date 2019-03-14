package com.mfeia.book.server_automaiton.integral_record;

import com.mfeia.book.server_automaiton.AddTestCases;
import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.PerformInspection;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestCasesIntegralRecord implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
        Map<String, String> headers = new HashMap<>(AutomationUtils.getMapHeaders());
        headers.put("uid", UserInfoUtils.getNewUserId());
        JSONObject jsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(IntegralRecordConfig.INTEGRAL_RECORD_EARNLIST, "curpage=1", headers)
        );
        performInspection.addtestFrameList(new EamList_ConversionList_SubsidyList(jsonObject,true,IntegralRecordConfig.INTEGRAL_RECORD_EARNLIST),number);
        jsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(IntegralRecordConfig.INTEGRAL_RECORD_COVERSIONLIST, "curpage=1", headers)
        );
        performInspection.addtestFrameList(new EamList_ConversionList_SubsidyList(jsonObject,false,IntegralRecordConfig.INTEGRAL_RECORD_COVERSIONLIST),number);
        jsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(IntegralRecordConfig.INTEGRAL_RECORD_SUBSIDYLIST, "curpage=1", headers)
        );
        performInspection.addtestFrameList(new EamList_ConversionList_SubsidyList(jsonObject,false,IntegralRecordConfig.INTEGRAL_RECORD_SUBSIDYLIST),number);
    }
}

package com.mfeia.book.server_automaiton.start_related;

import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;

public class TestCasesStartRelated implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws Exception {
        int vercode = Integer.parseInt(AutomationUtils.getServerAutomaitonProperties(AutomationUtils.VERCODE));
        int cnid = Integer.parseInt(AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID));
        JSONObject noUpdateJsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(StartRelatedConfig.START_RELATED_CHECK_VERSION,
                        "cnid=" + cnid +
                                "&vercode=" + (vercode)
                )
        );
        performInspection.addtestFrameList(new CheckVersion(noUpdateJsonObject, cnid, false,vercode), number);
        vercode--;
        JSONObject updateJsonObject = JSONObject.fromObject(
                AutomationUtils.doGet(StartRelatedConfig.START_RELATED_CHECK_VERSION,
                        "cnid=" + cnid +
                                "&vercode=" + (vercode)
                )
        );
        performInspection.addtestFrameList(new CheckVersion(updateJsonObject, cnid, true, vercode), number);

    }
}

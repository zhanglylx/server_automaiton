package com.mfeia.book.server_automaiton.shelf_related;

import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONObject;

import java.util.Map;

public class TestCasesShelfRelated implements AddTestCases {
    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) throws Exception {
        Map<String, String> headers = AutoHttpUtils.getMapHeaders();
        headers.put("Content-Type", "application/json");
        JSONObject jsonObject = JSONObject.fromObject(
                AutoHttpUtils.doPost(
                        ShelfRelatedConfig.SHELF_RELATED_SHELF_UPDATE,
                        (Object) AutomationUtils.
                                getServerAutomaitonProperties(
                                        ShelfRelatedConfig.SHELF_RELATED_SHELF_UPDATE_REQUEST_DATA),
                        headers, number, ShelfUpdate.class)
        );
        performInspection.addtestFrameList(new ShelfUpdate(jsonObject), number);
    }
}

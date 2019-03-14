package com.mfeia.book.server_automaiton.shelf_related;

import com.mfeia.book.server_automaiton.AddTestCases;
import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.PerformInspection;
import com.mfeia.book.server_automaiton.book_content.SubSidyMessage;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestCasesShelfRelated implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
        Map<String, String> headers = AutomationUtils.getMapHeaders();
        headers.put("Content-Type", "application/json");
        JSONObject jsonObject = JSONObject.fromObject(
                AutomationUtils.doPost(
                        ShelfRelatedConfig.SHELF_RELATED_SHELF_UPDATE,
                        (Object) AutomationUtils.
                                getServerAutomaitonProperties(
                                        ShelfRelatedConfig.SHELF_RELATED_SHELF_UPDATE_REQUEST_DATA),
                        headers)
        );
        performInspection.addtestFrameList(new ShelfUpdate(jsonObject), number);
    }
}

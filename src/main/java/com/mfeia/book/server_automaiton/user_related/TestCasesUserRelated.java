package com.mfeia.book.server_automaiton.user_related;

import com.mfeia.book.server_automaiton.AddTestCases;
import com.mfeia.book.server_automaiton.AutomationUtils;
import com.mfeia.book.server_automaiton.PerformInspection;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class TestCasesUserRelated implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
       performInspection.addtestFrameList( new MySidebar(number),number);
    }
}

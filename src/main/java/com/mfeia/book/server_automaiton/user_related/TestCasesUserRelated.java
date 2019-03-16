package com.mfeia.book.server_automaiton.user_related;

import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

public class TestCasesUserRelated implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
       performInspection.addtestFrameList( new MySidebar(number),number);
    }
}

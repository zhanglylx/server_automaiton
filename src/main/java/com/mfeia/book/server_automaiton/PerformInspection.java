package com.mfeia.book.server_automaiton;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 检查结果接口
 */
public interface PerformInspection {
    Map<Double, List<TestFrame>> checkResult();

    void addtestFrameList(TestFrame testFrame, double number);
    void addtestFrameList(TestFrame testFrame);
}

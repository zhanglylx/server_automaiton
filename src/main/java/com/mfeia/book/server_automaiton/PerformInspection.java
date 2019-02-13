package com.mfeia.book.server_automaiton;

import java.util.List;
import java.util.Map;

/**
 * 检查结果接口
 */
public interface PerformInspection {
    Map<Double, List<TestFrame>> checkResult();
    void addtestFrameList(TestFrame testFrame, double number);
}

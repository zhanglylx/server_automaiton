package server_automaiton_gather.server_automaiton_interface;

import server_automaiton_gather.TestFrame;

import java.util.List;
import java.util.Map;

/**
 * 检查结果接口
 */
public interface PerformInspection {
    Map<Double, List<TestFrame>> checkResult();

    void addtestFrameList(final TestFrame testFrame, double number);
    void addtestFrameList(final TestFrame testFrame);
}

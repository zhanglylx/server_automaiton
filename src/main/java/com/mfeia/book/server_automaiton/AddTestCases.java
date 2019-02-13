package com.mfeia.book.server_automaiton;

/**
 * 添加测试用例
 */
public interface AddTestCases {
    /**
     * 添加测试用例
     * @param performInspection
     */
    void additionTestCases(PerformInspection performInspection, double number) throws InterruptedException;
}

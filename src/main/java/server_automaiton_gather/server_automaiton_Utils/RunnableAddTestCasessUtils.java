package server_automaiton_gather.server_automaiton_Utils;

import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;

public class RunnableAddTestCasessUtils implements Runnable {

    private AddTestCases addTestCases;
    private double tag;

    public RunnableAddTestCasessUtils(AddTestCases addTestCases, double tabNumber) {
        this.addTestCases = addTestCases;
        this.tag = tabNumber;
    }

    public void run() {
        addTestCases(this.addTestCases);
    }

    private void addTestCases(AddTestCases addTestCase) {
        String className = "NULL";
        try {
            if (addTestCase == null) throw new NullPointerException();
            className = addTestCase.getClass().getName();
            addTestCase.additionTestCases(RealizePerform.getRealizePerform(), this.tag);
        } catch (Exception e) {
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(this.getClass(),
                    className + ":addTestCasesException", e, this.tag
            ), this.tag);
        }

    }

    public void setAddTestCases(AddTestCases addTestCases) {
        this.addTestCases = addTestCases;
    }

    public void setTag(double tag) {
        this.tag = tag;
    }



}


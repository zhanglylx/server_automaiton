package server_automaiton_gather;

import server_automaiton_gather.server_automaiton_Utils.HtmlUtils;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

import java.util.*;

/**
 * 实现检查接口
 */
public class RealizePerform implements PerformInspection {
    private Map<Double, List<TestFrame>> testFrameList;
    private final static RealizePerform realizePerform = new RealizePerform();
    private int defaultNumber = -1;
    private int succeedBranches = 0;
    private int failureBranches = 0;

    private RealizePerform() {
        this.testFrameList = Collections.synchronizedMap(new TreeMap<>());
    }

    public static RealizePerform getRealizePerform() {
//        if (realizePerform == null) {
//            synchronized (RealizePerform.class) {
//                if (realizePerform == null) {
//                    realizePerform = new RealizePerform();
//                }
//            }
//        }
        return realizePerform;
    }

    public void addtestFrameList(final TestFrame testFrame, double number) {
        if (testFrame == null) {
            addtestFrameList(
                    new ErrException(this.getClass(), "addtestFrameList",
                            new Exception(new NullPointerException()), number), number);
            return;
        }
        synchronized (this) {
            List<TestFrame> list = new ArrayList<>();
            if (this.testFrameList.containsKey(number)) {
                list = this.testFrameList.get(number);
                list.add(testFrame);
                this.testFrameList.put(number, list);
            } else {
                list.add(testFrame);
                this.testFrameList.put(number, list);
            }
            if (testFrame.setTag(number).stratCheck().checkCaseResult()) {
                this.succeedBranches++;
            } else {
                this.failureBranches++;
            }

        }

    }

    @Override
    public void addtestFrameList(final TestFrame testFrame) {
        addtestFrameList(testFrame, this.defaultNumber);
    }


    @Override
    public Map<Double, List<TestFrame>> checkResult() {
        synchronized (this) {
            return this.testFrameList;
        }
    }

    @Override
    public int getSucceedBranches() {
        return this.succeedBranches;
    }

    @Override
    public int getFailureBranches() {
        return this.failureBranches;
    }


    public Map<String, List<String>> getErrLoss() {
        return getLoss(false);
    }

    private Map<String, List<String>> getLoss(boolean MakeOrbreak) {
        Map<String, List<String>> loss = new TreeMap<>();
        synchronized (this) {
            Map.Entry<Double, List<TestFrame>> mapEntry;
            List<String> list;
            for (Iterator<Map.Entry<Double, List<TestFrame>>> iterator =
                 this.testFrameList.entrySet().iterator();
                 iterator.hasNext(); ) {
                mapEntry = iterator.next();
                list = new ArrayList<>();
                for (TestFrame testFrame : mapEntry.getValue()) {
                    if (testFrame.checkCaseResult() == MakeOrbreak) {
                        list.add(testFrame.toString());
                    }

                }
                loss.put(String.valueOf(mapEntry.getKey()), list);
            }
        }

        return loss;
    }

    @Override
    public Map<String, List<String>> getSucceedLoss() {
        return getLoss(true);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        synchronized (this) {
            Map.Entry<Double, List<TestFrame>> mapEntry;
            List<String> list;
            for (Iterator<Map.Entry<Double, List<TestFrame>>> iterator =
                 this.testFrameList.entrySet().iterator();
                 iterator.hasNext(); ) {
                mapEntry = iterator.next();
                stringBuilder.append(mapEntry.getKey());
                stringBuilder.append("\n");
                for (TestFrame testFrame : mapEntry.getValue()) {
                    stringBuilder.append(testFrame.toString());
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString();
    }


}

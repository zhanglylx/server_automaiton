package com.mfeia.book.server_automaiton;

import java.util.*;

/**
 * 实现检查接口
 */
public class RealizePerform implements PerformInspection {
    private Map<Double, List<TestFrame>> testFrameList;
    private static RealizePerform realizePerform = new RealizePerform();

    private RealizePerform() {
        this.testFrameList = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    static RealizePerform getRealizePerform() {
//        if (realizePerform == null) {
//            synchronized (RealizePerform.class) {
//                if (realizePerform == null) {
//                    realizePerform = new RealizePerform();
//                }
//            }
//        }
        return realizePerform;
    }

    public void addtestFrameList(TestFrame testFrame, double number) {
        if (testFrame == null) {
            addtestFrameList(
                    new ErrException("addtestFrameList",
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
        }
    }


    @Override
    public Map<Double, List<TestFrame>> checkResult() {
        synchronized (this) {
            return this.testFrameList;
        }
    }


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        synchronized (this) {
            Map.Entry<Double, List<TestFrame>> mapEntry;
            for (Iterator<Map.Entry<Double, List<TestFrame>>> iterator =
                 this.testFrameList.entrySet().iterator();
                 iterator.hasNext(); ) {
                mapEntry = iterator.next();
                stringBuilder.append(mapEntry.getKey());
                stringBuilder.append("\n");
                for (TestFrame testFrame : mapEntry.getValue()) {
                    stringBuilder.append(testFrame
                            .setTag(mapEntry.getKey())
                            .stratCheck()
                            .toString());
                    stringBuilder.append("\n");
                }
                iterator.remove();
            }


        }
        return stringBuilder.toString();
    }

}

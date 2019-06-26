package server_automaiton_gather.server_automaiton_Utils;

import ZLYUtils.JavaUtils;
import server_automaiton_gather.TestFrame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetCread {
    private static GetCread getCread = new GetCread();
    Map<Double, String> map = new LinkedHashMap<>();

    private GetCread() {

    }

    public static GetCread getCread() {
        return getCread;
    }

    public synchronized void filtrate(TestFrame testFrame, Object str) {
        if (str != null && str.toString().toLowerCase().contains("cread")) {
            this.map.put(testFrame.getTag(), testFrame.getCaseName());
        }
    }

    public synchronized void saveRecord() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter("cread.txt"), true);
        Map.Entry<Double,String> entry;
        for(Iterator<Map.Entry<Double,String>> iterator = this.map.entrySet().iterator();iterator.hasNext(); ){
            entry = iterator.next();
            printWriter.println(JavaUtils.strFormatting(entry.getKey(),entry.getValue()));
        }
        printWriter.close();
    }
}

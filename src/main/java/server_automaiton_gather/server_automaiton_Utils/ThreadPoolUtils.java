package server_automaiton_gather.server_automaiton_Utils;

import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolUtils {
    private static ThreadPoolExecutor executorService;

    static {
        try {
            executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                    Integer.parseInt(AutomationUtils.getServerAutomaitonProperties("executorService")));
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(
                    new ErrException(ThreadPoolUtils.class, "初始化：executorService", e)
            );
        }
    }

    public static int getExecutorServiceActiveCount() {
        return executorService.getActiveCount();
    }

    public static void addExecute(Runnable runnable) throws Exception {
        if (!executorService.isShutdown()) {
            executorService.execute(runnable);
        } else {
            throw new Exception("executorService Is power off");
        }

    }

    public static void executorServiceShutdown() {
        if (!executorService.isShutdown())
            executorService.shutdown();
    }

    public static boolean executorServiceisTerminated() {
        return executorService.isTerminated();
    }

    public static void waitThread() throws InterruptedException {
        while (getExecutorServiceActiveCount() != 0) {
            Thread.sleep(1000);
        }
    }

}

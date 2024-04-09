package ru.otus.executors;

public class PrinterMonitor {
    private String lastExecutedThread;

    public String getLastExecutedThread() {
        return lastExecutedThread;
    }

    public void setLastExecutedThread(String lastExecutedThread) {
        this.lastExecutedThread = lastExecutedThread;
    }

    public PrinterMonitor(String lastExecutedThread) {
        this.lastExecutedThread = lastExecutedThread;
    }
}

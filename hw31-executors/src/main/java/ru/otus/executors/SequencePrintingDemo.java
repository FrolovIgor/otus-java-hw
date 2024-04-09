package ru.otus.executors;

public class SequencePrintingDemo {
    public static void main(String[] args) {
        var t1Name = "t1";
        var t2Name = "t2";

        PrinterMonitor monitor = new PrinterMonitor(t2Name);
        var t1 = new Thread(() -> new SeqPrinter(monitor).printSequence(), t1Name);
        var t2 = new Thread(() -> new SeqPrinter(monitor).printSequence(), t2Name);

        t1.start();
        t2.start();
    }
}
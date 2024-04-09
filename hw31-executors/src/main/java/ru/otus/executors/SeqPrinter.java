package ru.otus.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeqPrinter {
    private static final Logger logger = LoggerFactory.getLogger(SeqPrinter.class);
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 10;
    private static final long SLEEP_DURATION = 500;
    private static final int ITERATIONS = 3;

    private int iterationsCount;
    private int currentValue;
    private boolean direction;
    private final PrinterMonitor monitor;
    private final String threadName;

    public SeqPrinter(PrinterMonitor monitor) {
        this.monitor = monitor;
        this.threadName = Thread.currentThread().getName();
        this.iterationsCount = 0;
        this.currentValue = 1;
        this.direction = false;
    }

    public void printSequence() {
        synchronized (monitor) {
            while (iterationsCount <= ITERATIONS) {
                while (threadName.equals(monitor.getLastExecutedThread())) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                logger.info("{}", currentValue);

                if (currentValue == MIN_VALUE || currentValue == MAX_VALUE) changeDirection();
                if (direction) {
                    currentValue++;
                } else {
                    currentValue--;
                }

                monitor.setLastExecutedThread(threadName);
                sleep();
                monitor.notifyAll();
            }
        }
    }

    private void changeDirection() {
        direction = !direction;
        iterationsCount++;
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);
    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final PriorityBlockingQueue<SensorData> dataBuffer;
    private final List<SensorData> bufferedData;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));
        this.bufferedData = new ArrayList<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
        dataBuffer.put(data);
    }

    public void flush() {
        try {
            if (readForFlash()) writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    private synchronized boolean readForFlash() {
        bufferedData.clear();
        if (!dataBuffer.isEmpty()) {
            while (!dataBuffer.isEmpty()) {
                try {
                    bufferedData.add(dataBuffer.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        } else return false;
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}

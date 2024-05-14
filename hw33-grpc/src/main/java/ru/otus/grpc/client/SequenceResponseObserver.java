package ru.otus.grpc.client;

import io.grpc.stub.StreamObserver;
import ru.otus.grpc.SequenceValue;

import java.util.logging.Logger;

public class SequenceResponseObserver implements StreamObserver<SequenceValue> {

    private static final Logger LOGGER = Logger.getGlobal();

    private long lastValue=0;

    @Override
    public void onNext(SequenceValue value) {
        LOGGER.info("Get new value: " + value);
        setLastValue(value.getSequenceValue());
    }

    @Override
    public void onError(Throwable e) {
        LOGGER.warning("Error: " + e);

    }

    @Override
    public void onCompleted() {
        LOGGER.info("Processing completed");
    }

    public long getLastValueAndReset() {
        var lastValuePrev = this.lastValue;
        this.lastValue = 0;
        return lastValuePrev;
    }

    public void setLastValue(long lastValue) {
        this.lastValue = lastValue;
    }
}

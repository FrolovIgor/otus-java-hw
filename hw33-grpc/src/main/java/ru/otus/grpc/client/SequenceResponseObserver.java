package ru.otus.grpc.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.SequenceValue;


public class SequenceResponseObserver implements StreamObserver<SequenceValue> {

    private static final Logger LOG = LoggerFactory.getLogger(SequenceResponseObserver.class);

    private long lastValue = 0;

    @Override
    public void onNext(SequenceValue value) {
        LOG.info("Number from server: {}", value);
        setLastValue(value.getSequenceValue());
    }

    @Override
    public void onError(Throwable e) {
        LOG.error("Got error: ", e);

    }

    @Override
    public void onCompleted() {
        LOG.info("Processing completed");
    }

    public synchronized long getLastValueAndReset() {
        var lastValuePrev = this.lastValue;
        this.lastValue = 0;
        return lastValuePrev;
    }

    private synchronized void setLastValue(long lastValue) {
        this.lastValue = lastValue;
    }
}

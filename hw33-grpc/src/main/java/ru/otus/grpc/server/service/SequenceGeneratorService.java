package ru.otus.grpc.server.service;


import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.SequenceGeneratorServiceGrpc;
import ru.otus.grpc.SequenceRequest;
import ru.otus.grpc.SequenceValue;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class SequenceGeneratorService extends SequenceGeneratorServiceGrpc.SequenceGeneratorServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(SequenceGeneratorService.class);
    private AtomicLong currentValue;

    public SequenceGeneratorService() {
        this.currentValue = new AtomicLong(0L);
    }

    @Override
    public void generateSequence(SequenceRequest request, StreamObserver<SequenceValue> responseObserver) {
        LOG.info("Receive new request: " + request.getFirstValue() + " to " + request.getLastValue());
        currentValue.set(request.getFirstValue());

        var executor = Executors.newScheduledThreadPool(1);

        var task = getProcessTask(responseObserver, request.getLastValue());

        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);

    }

    private Runnable getProcessTask(StreamObserver<SequenceValue> responseObserver, long lastValue) {
        return () -> {
            var nextValue = currentValue.incrementAndGet();
            var resp = SequenceValue.newBuilder().setSequenceValue(nextValue).build();
            responseObserver.onNext(resp);
            if (nextValue == lastValue) {
                responseObserver.onCompleted();
                LOG.info("Sequence generating finished");
            }
        };
    }
}

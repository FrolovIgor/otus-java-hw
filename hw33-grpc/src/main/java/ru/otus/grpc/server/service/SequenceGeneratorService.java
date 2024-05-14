package ru.otus.grpc.server.service;


import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.grpc.SequenceGeneratorServiceGrpc;
import ru.otus.grpc.SequenceRequest;
import ru.otus.grpc.SequenceValue;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;


public class SequenceGeneratorService extends SequenceGeneratorServiceGrpc.SequenceGeneratorServiceImplBase {

    private static final Logger LOGGER = Logger.getLogger("SequenceGeneratorService");

    @Override
    public void generateSequence(SequenceRequest request, StreamObserver<SequenceValue> responseObserver) {
        LOGGER.info("Receive new request: " + request.getFirstValue()+" to "+request.getLastValue());
        var currentValue = new AtomicLong(request.getFirstValue());

        var executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            var nextValue = currentValue.incrementAndGet();
            var resp = SequenceValue.newBuilder().setSequenceValue(nextValue).build();
            responseObserver.onNext(resp);
            if (nextValue == request.getLastValue()) {
                responseObserver.onCompleted();
                LOGGER.info("Sequence generating finished");
            }
        };

        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);


    }
}

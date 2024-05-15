package ru.otus.grpc.client;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.SequenceGeneratorServiceGrpc;
import ru.otus.grpc.SequenceRequest;

import java.io.IOException;

public class NumberSequenceClient {
    public static final int SERVER_PORT = 8190;
    private static final String SERVER_HOST = "localhost";
    private static final int LOOP_LIMIT = 50;
    private static final long FIRST_VALUE = 0;
    private static final long LAST_VALUE = 30;

    private static final Logger LOG = LoggerFactory.getLogger(NumberSequenceClient.class);
    private static long value = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        LOG.info("Client starting, wait information ...");

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        var stub = SequenceGeneratorServiceGrpc.newStub(channel);
        var responseObserver = new SequenceResponseObserver();

        var request = SequenceRequest.newBuilder()
                .setFirstValue(FIRST_VALUE)
                .setLastValue(LAST_VALUE)
                .build();



        stub.generateSequence(request, responseObserver);

        for (var i = 0; i < LOOP_LIMIT; i++) {
            LOG.info("currentValue: {}", getNextValue(responseObserver));
            sleep();
        }

        channel.shutdown();

    }

    private static long getNextValue(SequenceResponseObserver sequenceResponseObserver) {
        value = value + sequenceResponseObserver.getLastValueAndReset() + 1;
        return value;
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
package ru.otus.grpc.client;

import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.grpc.SequenceGeneratorServiceGrpc;
import ru.otus.grpc.SequenceRequest;
import ru.otus.grpc.server.service.SequenceGeneratorService;

import java.io.IOException;
import java.util.logging.Logger;

public class NumberSequenceClient {
    public static final int SERVER_PORT = 8190;
    private static final String SERVER_HOST = "localhost";
    private static final Integer LOOP_LIMIT = 5;

    private static final Logger LOGGER = Logger.getGlobal();
    private static long value = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.info("Client starting, wait information ...");

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = SequenceGeneratorServiceGrpc.newStub(channel);

        var request = SequenceRequest.newBuilder()
                .setFirstValue(0L)
                .setLastValue(30L)
                .build();

        var responseObserver = new SequenceResponseObserver();

        stub.generateSequence(request, responseObserver);

        for (var i = 0; i < LOOP_LIMIT; i++) {
            LOGGER.info("currentValue: "+getNextValue(responseObserver));
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
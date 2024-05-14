package ru.otus.grpc.server;

import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.grpc.server.service.SequenceGeneratorService;

import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
public class NumberSequenceServer {
    public static final int SERVER_PORT = 8190;
    private static final Logger LOGGER = Logger.getGlobal();

    public static void main(String[] args) throws IOException, InterruptedException {
        var server =
                ServerBuilder.forPort(SERVER_PORT)
                        .addService(new SequenceGeneratorService())
                        .build();
        server.start();
        LOGGER.info("server waiting for client connections...");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.awaitTermination();

    }

}
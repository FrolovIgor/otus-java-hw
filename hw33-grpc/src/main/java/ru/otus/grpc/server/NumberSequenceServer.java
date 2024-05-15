package ru.otus.grpc.server;

import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.server.service.SequenceGeneratorService;

import java.io.IOException;

@Slf4j
public class NumberSequenceServer {
    public static final int SERVER_PORT = 8190;
    private static final Logger LOG = LoggerFactory.getLogger(NumberSequenceServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        var server =
                ServerBuilder.forPort(SERVER_PORT)
                        .addService(new SequenceGeneratorService())
                        .build();
        server.start();
        LOG.info("server waiting for client connections...");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.awaitTermination();

    }

}
package ru.otus.ioc.config.local;

import ru.otus.ioc.appcontainer.api.AppComponent;
import ru.otus.ioc.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.ioc.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig2 {


    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(
            IOService ioService, PlayerService playerService, EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @SuppressWarnings("squid:S106")
    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}

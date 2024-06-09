package ru.otus.ioc.config.local;

import ru.otus.ioc.appcontainer.api.AppComponent;
import ru.otus.ioc.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.ioc.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig1 {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer() {
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

}

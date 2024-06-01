package ru.otus.webserver.services;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.webserver.core.repository.DataTemplateHibernate;
import ru.otus.webserver.core.repository.HibernateUtils;
import ru.otus.webserver.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.webserver.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.webserver.crm.model.Address;
import ru.otus.webserver.crm.model.Client;
import ru.otus.webserver.crm.model.ClientDTO;
import ru.otus.webserver.crm.model.Phone;
import ru.otus.webserver.crm.service.DBServiceClient;
import ru.otus.webserver.crm.service.DbServiceClientImpl;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private final DBServiceClient dbServiceClient;

    public ClientServiceImpl() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        ///
        dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    }

    @Override
    public List<Client> getAllClients() {
        log.info("Get all clients from database");
        return dbServiceClient.findAll();
    }

    @Override
    public Client save(ClientDTO clientDTO) {
        var savedClient = dbServiceClient.saveClient(dtoToClient(clientDTO));
        log.info("Successfully save new client, {}", savedClient);
        return savedClient;
    }

    private Client dtoToClient(ClientDTO clientDTO) {
        return new Client(
                clientDTO.name(),
                new Address(clientDTO.address()),
                List.of(new Phone(clientDTO.phone())));
    }
}

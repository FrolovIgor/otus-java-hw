package ru.otus.webserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.webserver.core.repository.DataTemplateHibernate;
import ru.otus.webserver.core.repository.HibernateUtils;
import ru.otus.webserver.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.webserver.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.webserver.crm.model.Address;
import ru.otus.webserver.crm.model.Client;
import ru.otus.webserver.crm.model.Phone;
import ru.otus.webserver.crm.service.DbServiceClientImpl;
import ru.otus.webserver.dao.InMemoryUserDao;
import ru.otus.webserver.dao.UserDao;
import ru.otus.webserver.server.ClientsWebServer;
import ru.otus.webserver.server.ClientsWebServerWithFilterBasedSecurity;
import ru.otus.webserver.services.*;
import ru.otus.webserver.services.impl.ClientServiceImpl;
import ru.otus.webserver.services.impl.TemplateProcessorImpl;
import ru.otus.webserver.services.impl.UserAuthServiceImpl;

public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        UserDao adminDao = new InMemoryUserDao();

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        executeMigrations(configuration);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        ClientService clientService = new ClientServiceImpl(new DbServiceClientImpl(transactionManager, clientTemplate));
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(adminDao);

        ClientsWebServer clientsWebServer = new ClientsWebServerWithFilterBasedSecurity(
                WEB_SERVER_PORT, authService, clientService, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }

    private static void executeMigrations(Configuration configuration) {
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
    }
}

package ru.otus.webserver.server;

import com.google.gson.Gson;
import org.eclipse.jetty.ee10.servlet.FilterHolder;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Handler;
import ru.otus.webserver.dao.AdminDao;
import ru.otus.webserver.services.ClientService;
import ru.otus.webserver.services.TemplateProcessor;
import ru.otus.webserver.services.AdminAuthService;
import ru.otus.webserver.servlet.AuthorizationFilter;
import ru.otus.webserver.servlet.LoginServlet;

import java.util.Arrays;

public class UsersWebServerWithFilterBasedSecurity extends UsersWebServerSimple {
    private final AdminAuthService authService;

    public UsersWebServerWithFilterBasedSecurity(
            int port, AdminAuthService authService, ClientService clientService, Gson gson, TemplateProcessor templateProcessor) {
        super(port, clientService, gson, templateProcessor);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths)
                .forEachOrdered(
                        path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}

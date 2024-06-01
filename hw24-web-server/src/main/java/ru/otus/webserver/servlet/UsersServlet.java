package ru.otus.webserver.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.webserver.dao.AdminDao;
import ru.otus.webserver.services.ClientService;
import ru.otus.webserver.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"squid:S1948"})
public class UsersServlet extends HttpServlet {

    private static final String CLIENTS_BASE_TEMPLATE = "clients.html";

    private final ClientService clientService;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, ClientService clientService) {
        this.templateProcessor = templateProcessor;
        this.clientService = clientService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_BASE_TEMPLATE, paramsMap));
    }
}

package ru.otus.webserver.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.webserver.services.ClientService;
import ru.otus.webserver.crm.model.ClientDTO;

import java.io.IOException;
import java.util.stream.Collectors;

public class ClientsApiServlet extends HttpServlet {

    private final ClientService clientService;
    private final Gson gson;

    public ClientsApiServlet(ClientService clientService, Gson gson) {
        this.clientService = clientService;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.print(gson.toJson(clientService.getAllClients()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var clientDto = getBodyFromRequest(req);
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.print(gson.toJson(clientService.save(clientDto)));
    }

    private ClientDTO getBodyFromRequest(HttpServletRequest req) throws IOException {
        var strBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return gson.fromJson(strBody, ClientDTO.class);
    }
}

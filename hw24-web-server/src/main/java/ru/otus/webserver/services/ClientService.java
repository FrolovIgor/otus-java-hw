package ru.otus.webserver.services;

import ru.otus.webserver.crm.model.Client;
import ru.otus.webserver.crm.model.ClientDTO;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    Client save(ClientDTO client);
}

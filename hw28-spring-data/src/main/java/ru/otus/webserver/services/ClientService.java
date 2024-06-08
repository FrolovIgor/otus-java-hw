package ru.otus.webserver.services;

import ru.otus.webserver.domain.Client;
import ru.otus.webserver.domain.ClientDTO;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();
    Client save(ClientDTO client);
}

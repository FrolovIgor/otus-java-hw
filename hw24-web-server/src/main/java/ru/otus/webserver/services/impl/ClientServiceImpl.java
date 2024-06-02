package ru.otus.webserver.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.webserver.crm.model.Address;
import ru.otus.webserver.crm.model.Client;
import ru.otus.webserver.crm.model.ClientDTO;
import ru.otus.webserver.crm.model.Phone;
import ru.otus.webserver.crm.service.DBServiceClient;
import ru.otus.webserver.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final DBServiceClient dbServiceClient;

    public ClientServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        log.info("Get all clients from database");
        return dbServiceClient.findAll().stream().map(this::clientToDTO).toList();
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
                clientDTO.phones().stream().map(Phone::new).toList());
    }

    private ClientDTO clientToDTO(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getAddress().getStreet(),
                client.getPhones().stream().map(Phone::getNumber).collect(Collectors.toList()));
    }
}

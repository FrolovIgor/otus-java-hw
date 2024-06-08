package ru.otus.webserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import ru.otus.webserver.domain.Address;
import ru.otus.webserver.domain.Client;
import ru.otus.webserver.domain.ClientDTO;
import ru.otus.webserver.domain.Phone;
import ru.otus.webserver.repository.ClientsRepository;
import ru.otus.webserver.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientsRepository clientsRepository;

    @Override
    public List<ClientDTO> getAllClients() {
        log.info("Get all clients from database");
        return clientsRepository.findAll().stream().map(this::clientToDTO).toList();
    }

    @Override
    public Client save(ClientDTO clientDTO) {
        var savedClient = clientsRepository.save(dtoToClient(clientDTO));
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

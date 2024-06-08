package ru.otus.webserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.webserver.domain.Client;
import ru.otus.webserver.domain.ClientDTO;
import ru.otus.webserver.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<ClientDTO> getClients() {
        log.info("GET /api/client");
        return clientService.getAllClients();
    }

    @PostMapping
    public Client saveClient(@RequestBody ClientDTO clientDTO) {
        log.info("POST /api/client, clientDTO: {}", clientDTO);
        return clientService.save(clientDTO);
    }
}

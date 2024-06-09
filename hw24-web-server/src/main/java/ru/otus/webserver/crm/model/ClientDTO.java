package ru.otus.webserver.crm.model;

import java.util.List;

public record ClientDTO(long id, String name, String address, List<String> phones) {
}

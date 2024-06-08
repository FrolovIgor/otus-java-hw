package ru.otus.webserver.domain;

import java.util.List;

public record ClientDTO(long id, String name, String address, List<String> phones) {
}

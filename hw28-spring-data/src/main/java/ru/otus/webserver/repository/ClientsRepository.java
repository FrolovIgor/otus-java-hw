package ru.otus.webserver.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.webserver.domain.Client;

@Repository
public interface ClientsRepository extends ListCrudRepository<Client, Long> {
}

package ru.otus.webserver.dao;

import ru.otus.webserver.model.Admin;

import java.util.Optional;

public interface AdminDao {
    Optional<Admin> findByLogin(String login);
}

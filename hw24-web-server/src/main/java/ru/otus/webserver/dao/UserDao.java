package ru.otus.webserver.dao;

import ru.otus.webserver.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByLogin(String login);
}

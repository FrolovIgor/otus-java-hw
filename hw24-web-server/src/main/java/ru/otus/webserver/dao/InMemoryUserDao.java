package ru.otus.webserver.dao;


import ru.otus.webserver.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserDao implements UserDao {
    public static final String DEFAULT_PASSWORD = "test123";
    private final Map<Long, User> admins;

    public InMemoryUserDao() {
        admins = new HashMap<>();
        admins.put(1L, new User(1L, "admin", DEFAULT_PASSWORD));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return admins.values().stream().filter(v -> v.login().equals(login)).findFirst();
    }
}

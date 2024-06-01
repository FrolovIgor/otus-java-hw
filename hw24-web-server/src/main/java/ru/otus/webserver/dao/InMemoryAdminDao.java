package ru.otus.webserver.dao;


import ru.otus.webserver.model.Admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAdminDao implements AdminDao {
    public static final String DEFAULT_PASSWORD = "test123";
    private final Map<Long, Admin> admins;

    public InMemoryAdminDao() {
        admins = new HashMap<>();
        admins.put(1L, new Admin(1L, "admin", DEFAULT_PASSWORD));
    }

    @Override
    public Optional<Admin> findByLogin(String login) {
        return admins.values().stream().filter(v -> v.login().equals(login)).findFirst();
    }
}

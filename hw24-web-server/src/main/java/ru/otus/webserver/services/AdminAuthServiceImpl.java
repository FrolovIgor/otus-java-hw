package ru.otus.webserver.services;

import ru.otus.webserver.dao.AdminDao;

public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminDao userDao;

    public AdminAuthServiceImpl(AdminDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userDao.findByLogin(login)
                .map(user -> user.password().equals(password))
                .orElse(false);
    }
}

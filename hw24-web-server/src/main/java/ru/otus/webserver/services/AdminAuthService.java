package ru.otus.webserver.services;

public interface AdminAuthService {
    boolean authenticate(String login, String password);
}

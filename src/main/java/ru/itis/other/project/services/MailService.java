package ru.itis.other.project.services;

public interface MailService {

    void send(String address, String subject, String html);
}

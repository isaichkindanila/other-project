package ru.itis.other.project.services;

import ru.itis.other.project.util.annotations.DoNotLog;

public interface MailService {

    void send(String address, String subject, @DoNotLog String html);
}

package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.itis.other.project.services.MailService;
import ru.itis.other.project.util.DoNotLog;

import java.util.concurrent.Executor;

@Service
@AllArgsConstructor
class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final Executor executor;

    @Override
    @DoNotLog
    @SneakyThrows
    public void send(String address, String subject, String html) {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setText(html, true);
        helper.setTo(address);

        executor.execute(() -> mailSender.send(message));
    }
}

package ru.itis.other.project.listener;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var input = getClass().getResourceAsStream("/init.sql").readAllBytes();
        var sql = new String(input, StandardCharsets.UTF_8);

        event.getApplicationContext().getBean(JdbcTemplate.class).execute(sql);
    }
}

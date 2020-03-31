package ru.itis.other.project.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@AllArgsConstructor
public class AppConfig {

    private final Environment env;

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(env.getRequiredProperty("db.url"));
        config.setUsername(env.getRequiredProperty("db.username"));
        config.setPassword(env.getRequiredProperty("db.password"));
        config.setDriverClassName(env.getRequiredProperty("db.driver"));

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }
}

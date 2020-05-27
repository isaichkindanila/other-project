package ru.itis.other.project.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.Configuration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// conflicts with Freemarker's `Configuration` class
@org.springframework.context.annotation.Configuration
@PropertySource("classpath:application.properties")
@EnableWebMvc
@EnableAspectJAutoProxy
@AllArgsConstructor
public class AppConfig {

    private final Environment env;

    @Bean
    @SuppressWarnings("ConstantConditions")
    public Executor executor() {
        int threads = Integer.parseInt(env.getProperty("executor.threads"));
        return Executors.newFixedThreadPool(threads);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public DataSource hikariDataSource() {
        var config = new HikariConfig();

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

    @Bean
    public Configuration freemarkerConfiguration() {
        var configuration = new Configuration(Configuration.VERSION_2_3_29);

        configuration.setClassForTemplateLoading(getClass(), "/templates/");
        configuration.setDefaultEncoding("UTF-8");

        return configuration;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() {
        var configurer = new FreeMarkerConfigurer();
        configurer.setConfiguration(freemarkerConfiguration());

        return configurer;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        var mailProperties = new Properties();

        mailProperties.setProperty("mail.smtp.auth", env.getRequiredProperty("mail.smtp.auth"));
        mailProperties.setProperty("mail.smtp.starttls.enable", env.getRequiredProperty("mail.smtp.starttls.enable"));

        var sender = new JavaMailSenderImpl();

        sender.setJavaMailProperties(mailProperties);
        sender.setHost(env.getRequiredProperty("mail.host"));
        sender.setPort(Integer.parseInt(env.getRequiredProperty("mail.port")));
        sender.setUsername(env.getRequiredProperty("mail.username"));
        sender.setPassword(env.getRequiredProperty("mail.password"));

        return sender;
    }
}

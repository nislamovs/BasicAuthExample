package com.example.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.apache.commons.lang3.StringUtils.split;

@Configuration
public class EmailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(env.getRequiredProperty("email.smtp.host"));
        sender.setPort(env.getRequiredProperty("email.smtp.port", Integer.class));
        sender.setProtocol(env.getRequiredProperty("email.smtp.protocol"));
        sender.setUsername(env.getRequiredProperty("alert.email.username"));
        sender.setPassword(env.getRequiredProperty("alert.email.password"));

        return sender;
    }
}

package com.example.service;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendMail(String msg) {
        System.out.println("We are sending mail");
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@noreply.com");
            helper.setTo("your_mail@inbox.lv");
            helper.setSubject("Confirmation letter");
            helper.setText("Please follow this link. \n " + msg);
        } catch (MessagingException e) {
            System.out.println("Failed to parse email.");
        }

        sendMessage(message);
    }

    @Override
    public void sendActivationMail(User user) {
        System.out.println("We are sending activation mail");
        String link = "http://localhost:8080/activateuser/" + "?username=" + user.getEmail() + "&keyword=" + user.getKeyword();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@noreply.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Confirmation letter");
            helper.setText("Hi " + user.getName() + "!\n" + "Please follow this link: \n " + link + "\n\nThank you for choosing DashboardIO!");
        } catch (MessagingException e) {
            System.out.println("Failed to parse email.");
        }

        sendMessage(message);
    }

    @Override
    public void sendNewPasswordMail(User user, String passwd) {
        System.out.println("We are sending activation mail");
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@noreply.com");
            helper.setTo(user.getEmail());
            helper.setSubject("New password");
            helper.setText("Hi " + user.getName() + "!\n" + "Your new password is: \n " + passwd );
        } catch (MessagingException e) {
            System.out.println("Failed to parse email.");
        }

        sendMessage(message);
    }

    private void sendMessage(MimeMessage message) {
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            System.out.println("Failed to send alert email " + e.toString());
        }
    }

}

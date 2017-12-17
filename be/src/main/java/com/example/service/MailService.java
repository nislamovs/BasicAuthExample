package com.example.service;

import com.example.model.User;

import java.util.List;

public interface MailService {
    public void sendMail(String message);
    public void sendActivationMail(User user);
    public void sendNewPasswordMail(User user, String passwd);
}

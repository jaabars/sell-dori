package com.example.sellservicespringproject.services.impl;

import com.example.sellservicespringproject.services.SendSimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendSimpleMessageImpl implements SendSimpleMessage {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tueduev@gmail.com");
        message.setTo(to);
        message.setSubject("Код подтверждения");
        message.setText("Ваш код подтверждения: " + text);
        mailSender.send(message);
    }
}
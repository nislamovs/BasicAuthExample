package com.example.controller;

import com.example.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagController {

    @Autowired
    MailService mailService;

    @GetMapping(value = "/mail/")
    public ResponseEntity<?> sendMail() {

        mailService.sendMail("test msg");
        return new ResponseEntity<String>("{ok}", HttpStatus.OK);
    }
}

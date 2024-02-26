package com.example.EmailSending.Api.controller;

import com.example.EmailSending.Api.service.MailjetService;
import com.mailjet.client.errors.MailjetException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final MailjetService mailjetService;

    public EmailController(MailjetService mailjetService) {
        this.mailjetService = mailjetService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestParam String recipientEmail,
                                            @RequestParam String subject,
                                            @RequestParam String content) {
        try {
            mailjetService.sendPromotionalEmail(recipientEmail, subject, content);
            return ResponseEntity.ok("Email sent successfully");
        } catch (MailjetException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
        }
    }
}


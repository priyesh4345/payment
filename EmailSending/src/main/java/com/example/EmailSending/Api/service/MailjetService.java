package com.example.EmailSending.Api.service;

import com.example.EmailSending.Api.payload.MailjetConfig;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MailjetService {

    private final MailjetConfig mailjetConfig;

    public MailjetService(MailjetConfig mailjetConfig) {
        this.mailjetConfig = mailjetConfig;
    }

    public void sendPromotionalEmail(String recipientEmail, String subject, String content) throws MailjetException {
        MailjetClient client = new MailjetClient(mailjetConfig.getApiKey(), mailjetConfig.getApiSecret());
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "your-sender-email@example.com")
                                        .put("Name", "Your Name"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", recipientEmail)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.HTMLPART, content)));

        MailjetResponse response = client.post(request);
        if (response.getStatus() != 200) {
            throw new MailjetException("Failed to send email. Status: " + response.getStatus());
        }
    }
}

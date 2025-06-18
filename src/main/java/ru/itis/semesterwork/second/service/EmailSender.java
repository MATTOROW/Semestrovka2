package ru.itis.semesterwork.second.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSender {

    @Value("${email.api-key}")
    private String API_KEY;

    @Value("${email.email}")
    private String SENDER_EMAIL;

    @Value("${email.name}")
    private String SENDER_NAME;

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson

    public void sendEmail(String email, String token) {
        String subject = "Password Reset Code";
        String text = "Your password reset code is: " + token + "\n\nThis code is valid for 30 minutes.";

        try {
            Request request = new Request.Builder()
                    .url(fullUrl(email, text, subject))
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Failed to send email. HTTP " + response.code() + ": " + responseBody);
                }
                System.out.println("Email sent: " + responseBody);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to send email via UniSender.", e);
        }
    }

    public String fullUrl(String email, String message, String subject) {
        StringBuilder url = new StringBuilder("https://api.unisender.com/ru/api/sendEmail");
        url.append("?format=json");
        url.append("&api_key=").append(URLEncoder.encode(API_KEY, StandardCharsets.UTF_8));
        url.append("&email=").append(URLEncoder.encode("Notification taker <%s>".formatted(email), StandardCharsets.UTF_8));
        url.append("&sender_name=").append(URLEncoder.encode(SENDER_NAME, StandardCharsets.UTF_8));
        url.append("&sender_email=").append(URLEncoder.encode(SENDER_EMAIL, StandardCharsets.UTF_8));
        url.append("&subject=").append(URLEncoder.encode(subject, StandardCharsets.UTF_8));
        url.append("&body=").append(URLEncoder.encode(message, StandardCharsets.UTF_8));
        url.append("&list_id=").append(1);
        return url.toString();
    }
}

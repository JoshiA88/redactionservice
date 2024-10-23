package com.example.redactionservice.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redact")
public class RedactionController {

    @Value("${redaction.words}")
    private List<String> redactionWords;

    // Endpoint to identify the service
    @GetMapping
    public String identifyService() {
        return "Redaction Service";
    }

    // POST endpoint to redact words
    @PostMapping
    public String redactText(@RequestBody String text) throws IOException {
        logRequest(text);
        return redactWords(text);
    }

    // Method to replace words in the text with "REDACTED"
    private String redactWords(String text) {
        for (String word : redactionWords) {
            text = text.replaceAll("(?i)\\b" + word + "\\b", "REDACTED"); // Case insensitive replacement
        }
        return text;
    }

    // Method to log the request to a file with timestamp
    private void logRequest(String text) throws IOException {
        try (FileWriter fw = new FileWriter("requests.log", true)) {
            fw.write(LocalDateTime.now() + " - Original Text: " + text + "\n");
        }
    }
}

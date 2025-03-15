package com.profitkey.stock.dto.openai;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;
    private double temperature = 0.7;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
} 
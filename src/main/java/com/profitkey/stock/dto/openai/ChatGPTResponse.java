package com.profitkey.stock.dto.openai;

import java.util.List;
import lombok.Data;

@Data
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
        private int index;
        private String finish_reason;
    }
} 
package com.profitkey.stock.controller;

import org.springframework.web.bind.annotation.*;

import com.profitkey.stock.service.OpenAIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/openai")
@RequiredArgsConstructor
public class OpenAIController {
    
    private final OpenAIService openAIService;
    
    @PostMapping("/chat")
    public String chat() {
        return openAIService.generateResponse();
    }
} 
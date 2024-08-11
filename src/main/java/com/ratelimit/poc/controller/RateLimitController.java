package com.ratelimit.poc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ratelimit.poc.service.RateLimitingService;

@RestController
public class RateLimitController {
    private final RateLimitingService rateLimitingService;

    public RateLimitController(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Autowired
    private HttpServletRequest request;
    
    @GetMapping("/resource")
    public String getTest(){
        String apiKey = request.getHeader("rate-limit-api-key"); // Retrieve API key from request headers or JWT token
        if (rateLimitingService.allowRequest(apiKey)) {
            return "Resource api accessed successfully";
        } else {
            return "Rate limit exceeded. Please Try again later.";
        }
    }
}

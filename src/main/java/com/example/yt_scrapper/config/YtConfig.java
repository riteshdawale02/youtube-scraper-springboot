package com.example.yt_scrapper.config;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class YtConfig {
    
    private String apiUrl = "https://www.googleapis.com/youtube/v3/videos";
    private String apiKey = "{Add Your API Key Here}";

}

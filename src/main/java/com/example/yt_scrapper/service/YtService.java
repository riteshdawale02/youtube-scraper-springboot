package com.example.yt_scrapper.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.yt_scrapper.config.YtConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class YtService {

    @Autowired
    private YtConfig ytConfig;

    public String extractVideoId(String videoLink){
        Pattern urlPattern = Pattern.compile("(?<=watch\\?v=|/videos/|youtu\\.be/|embed/)([a-zA-Z0-9_-]{11})"
                    ,Pattern.CASE_INSENSITIVE);
                    
        Matcher matcher = urlPattern.matcher(videoLink);

        if(matcher.find()){
            return matcher.group(1);
        }else{
            return null;
        }
    }

    public JsonNode getVideoDetails(String videoId) throws Exception{

        //API integration- using the rest template

        String apiurl = ytConfig.getApiUrl();
        String apiKey = "key="+ytConfig.getApiKey();

        String idParam = "id="+videoId;
        String partParam = "part=snippet";

        String url= apiurl +"?"+ apiKey + "&" + partParam + "&" + idParam;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

//        System.out.println(response);
         
        
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response).path("items").get(0).path("snippet");


    }
}

package com.example.yt_scrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.yt_scrapper.service.YtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class YtController {
    
    @Autowired
    private YtService ytService;
    
    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @PostMapping("/processing")
    public String getData (@RequestParam String videoLink, Model model) { //requestParam taking input from the user
        String videoId = ytService.extractVideoId(videoLink);
        System.out.println("Video - id: "+ videoId);

        if(videoId != null){
            try{

                JsonNode videoDetails = ytService.getVideoDetails(videoId);
                String title = videoDetails.path("title").asText();
                String description = videoDetails.path("description").asText();
                String thumUrl = videoDetails.path("thumbnails").path("standard").path("url").asText();
                String tags[] = new ObjectMapper().treeToValue(videoDetails.path("tags"),String[].class);

                model.addAttribute("vtitle",title);
                model.addAttribute("vdescr",description);
                model.addAttribute("vthumb",thumUrl);
                model.addAttribute("vtags",tags);

                // System.out.println("DFKSDFLSDKFJSLDFKJS"+title);
                return "details";
            } catch  (Exception e){
                System.out.println(e);
                return "error";
            }
        }

        return "error";
    }
}

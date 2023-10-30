package com.example.rss.controller;

import com.example.rss.service.RssService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController {

    private final RssService rssService;

    public FeedController(RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping(value = "/get")
    public void get(){
        rssService.fetchData();
    }
}

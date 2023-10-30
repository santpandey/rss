package com.example.rss.service;

import com.example.rss.domain.FeedConfiguration;
import com.example.rss.repository.FeedRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RssService {

    private String url;
    private FeedRepository feedRepository;
    public RssService(@Value("${rss.url}") String url, FeedRepository feedRepository) {
        this.url = url;
        this.feedRepository = feedRepository;
    }

    public void fetchData(){
        log.info("Querying configuration Table");

        Map<String,List<String>> externalUrlsMap = feedRepository.findByEnabled(true).stream()
                .collect(Collectors.groupingBy(FeedConfiguration::getId,
                        Collectors.mapping(FeedConfiguration::getLink, Collectors.toList())));
        List<String> activeUrls = feedRepository.findByEnabled(true).stream()
                .map(FeedConfiguration::getLink)
                .collect(Collectors.toList());

        externalUrlsMap.entrySet().stream()
                .forEach(entry ->{
                    String feedId = entry.getKey();
                    //call external API
                    call(entry.getValue().get(0));

                });

    }

    private WebClient getWebClient(){
        return WebClient.create(url);
    }

    private void call(String externalUrl) {
        XmlMapper xmlMapper = XmlMapper.builder().build();
        WebClient webClient = getWebClient();
        externalUrl = externalUrl.split(url)[1];
        Flux<FeedConfiguration> response = webClient.get()
                .uri(externalUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(FeedConfiguration.class);
        log.info("Response is "+response);

    }

}

package com.example.rss.domain;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class FeedData {
    @Id
    String id;
    String title;
    String description;
    String link;
    String publishedDate;
}

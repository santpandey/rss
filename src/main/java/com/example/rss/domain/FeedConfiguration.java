package com.example.rss.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CONFIGURATION_TABLE")
public class FeedConfiguration {

    @Id
    String id;
    String link;
    boolean enabled;
}

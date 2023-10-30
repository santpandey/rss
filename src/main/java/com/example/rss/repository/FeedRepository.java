package com.example.rss.repository;

import com.example.rss.domain.FeedConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<FeedConfiguration, String> {

    List<FeedConfiguration> findByEnabled(boolean value);
}

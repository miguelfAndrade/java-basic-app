package com.example.basicapp;

import org.springframework.data.mongodb.repository.MongoRepository;

// Class responsible for creating the queries that goes to the mongoDB
public interface SiteRepository extends MongoRepository<Site, String> {
    public Site findByUrl(String url);
}

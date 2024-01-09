package com.martinzav.marvelChallenge.persistence.integration.marvel.dto;


public record ComicDto(Long id, String title, String description, String modified, String resourceUri, ThumbnailDto thumbnail) {
    
}

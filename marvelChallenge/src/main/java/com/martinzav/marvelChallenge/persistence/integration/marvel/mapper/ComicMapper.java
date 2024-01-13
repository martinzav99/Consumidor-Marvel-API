package com.martinzav.marvelChallenge.persistence.integration.marvel.mapper;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ComicDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ThumbnailDto;

public class ComicMapper {
    
    public static List<ComicDto> toDtoList(JsonNode rootNode){
        List<ComicDto> comicsDtoList = new ArrayList<>();

        getResults(rootNode).elements().forEachRemaining(nodeI -> {
            comicsDtoList.add(ComicMapper.toDto(nodeI));
        });
        return comicsDtoList;
    }

    private static ArrayNode getResults(JsonNode rootNode){

        if(rootNode == null)
            throw new IllegalArgumentException("El nodo json no puede ser null");

        JsonNode dataNode = rootNode.get("data");
        return (ArrayNode) dataNode.get("results");
    }

    private static ComicDto toDto(JsonNode nodeI) {
        if(nodeI == null)
            throw new IllegalArgumentException("El nodo json no puede ser null");

        ThumbnailDto thumbnailDto = ThumbnailMapper.toDto(nodeI.get("thumbnail"));

        ComicDto comicDto = new ComicDto(nodeI.get("id").asLong(),
        nodeI.get("title").asText(),
        nodeI.get("description").asText(),
        nodeI.get("modified").asText(),
        nodeI.get("resourceUri").asText(),
        thumbnailDto);

        return comicDto;
    }
}

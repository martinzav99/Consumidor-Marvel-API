package com.martinzav.marvelChallenge.webController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ComicDto;
import com.martinzav.marvelChallenge.service.ComicService;

@RestController
@RequestMapping("/comics")
public class ComicController {
    
    @Autowired
    private ComicService comicService;

    @PreAuthorize("hasAuthority('comic:read-all')")
    @GetMapping
    public ResponseEntity<List<ComicDto>> findAll(@RequestParam(required = false) Long characterId,
                                                  @RequestParam(defaultValue = "0") Long offset,
                                                  @RequestParam(defaultValue = "10") Long limit){
        MyPageable pageable = new MyPageable(offset, limit);
        return ResponseEntity.ok(comicService.findAll(characterId,pageable));
    }

    @PreAuthorize("hasAuthority('comic:read-by-id')")
    @GetMapping("/{comicId}")
    public ResponseEntity<ComicDto> findById(@PathVariable Long comicId){
        return ResponseEntity.ok(comicService.findById(comicId));
    }
}

package com.martinzav.marvelChallenge.webController.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.martinzav.marvelChallenge.dto.UserInteractionLogDto;
import com.martinzav.marvelChallenge.service.UserInteractionLogService;

@RestController
@RequestMapping("/users-interaction")
public class UserInteractionLogController {

    @Autowired
    private UserInteractionLogService userInteractionLogService;
    
    @GetMapping
    public ResponseEntity<Page<UserInteractionLogDto>> findAll(@RequestParam(defaultValue = "0") int offset,
                                                               @RequestParam(defaultValue = "10")int limit){

        Pageable pageable = buildPageable(offset, limit);
        
        return ResponseEntity.ok(userInteractionLogService.findAll(pageable));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Page<UserInteractionLogDto>> findByUsername(@PathVariable String username,
                                                                      @RequestParam(defaultValue = "0") int offset,
                                                                      @RequestParam(defaultValue = "20")int limit){
        Pageable pageable = buildPageable(offset, limit);

        return ResponseEntity.ok(userInteractionLogService.findByUsername(pageable,username));                                                                
    }

    private Pageable buildPageable(int offset, int limit) {
        Pageable pageable = null;

        if (offset<0)
           throw new IllegalArgumentException("El offset no puede ser negativo");
        if (limit <=0)
           throw new IllegalArgumentException("El limit no puede ser negativo");

        if (offset == 0)
           pageable = PageRequest.of(0, limit);
        else
           pageable = PageRequest.of(offset/limit, limit);
        return pageable;
    }
    
}

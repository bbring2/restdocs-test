package com.example.restdocs.controller;

import com.example.restdocs.entity.dto.req.PostRequestDto;
import com.example.restdocs.entity.dto.res.PostResponseDto;
import com.example.restdocs.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<PostResponseDto.TotalDto> register(@RequestBody PostRequestDto.RegisterDto request) {
        final PostResponseDto.TotalDto response = service.register(request);
        return ResponseEntity.created(URI.create("/post/" + response.getId())).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto.TotalDto> searchById(@PathVariable final Long postId) {
        return ResponseEntity.ok(service.searchById(postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable final Long postId) {
        service.deleted(postId);
        return ResponseEntity.noContent().build();
    }

}

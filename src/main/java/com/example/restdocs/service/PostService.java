package com.example.restdocs.service;

import com.example.restdocs.entity.Post;
import com.example.restdocs.entity.dto.req.PostRequestDto;
import com.example.restdocs.entity.dto.res.PostResponseDto;
import com.example.restdocs.entity.User;
import com.example.restdocs.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    private final UserService userService;

    public Post getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException();
                });
    }

    public PostResponseDto.TotalDto register(PostRequestDto.RegisterDto request) {
        User user = userService.getById(request.getUserId());

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .views(0)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .build();

        return PostResponseDto.TotalDto.toDto(repository.save(post));
    }

    public PostResponseDto.TotalDto searchById(Long postId) {
        return PostResponseDto.TotalDto.toDto(getById(postId));
    }

    public void deleted(Long postId) {
        Post post = getById(postId);

        if(post.isDeleted()) {
            throw new RuntimeException(new Exception("이미 삭제되었습니다!"));
        }

        post.deleted();
        repository.save(post);
    }
}

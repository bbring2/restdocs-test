package com.example.restdocs.entity.dto.res;

import com.example.restdocs.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TotalDto{
        private Long id;
        private UserResponseDto.TotalDto userInfo;
        private String title;
        private String content;
        private int views;
        private boolean deleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static PostResponseDto.TotalDto toDto(Post post) {
            return TotalDto.builder()
                    .id(post.getId())
                    .userInfo(UserResponseDto.TotalDto.toDto(post.getUser()))
                    .title(post.getTitle())
                    .content(post.getContent())
                    .views(post.getViews())
                    .deleted(post.isDeleted())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
        }
    }
}

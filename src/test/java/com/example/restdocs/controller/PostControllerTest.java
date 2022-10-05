package com.example.restdocs.controller;

import com.example.restdocs.entity.dto.res.PostResponseDto;
import com.example.restdocs.entity.dto.res.UserResponseDto;
import com.example.restdocs.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class) // When using JUnit5
@SpringBootTest
class PostControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PostService service;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void create() throws Exception {
        final UserResponseDto.TotalDto userResponse = new UserResponseDto.TotalDto(1L, "최희정", "test@test.com", false, LocalDateTime.now(), null);
        final PostResponseDto.TotalDto postResponse = new PostResponseDto.TotalDto(1L, userResponse, "title", "content", 0, false, LocalDateTime.now(), null);

        when(service.register(any())).thenReturn(postResponse);

        this.mockMvc.perform(post("/post") // 1
                        .content("{\"title\": \"title\", \n\"content\": \"content\"}") // 2
                        .contentType(MediaType.APPLICATION_JSON)) // 3
                .andExpect(status().isCreated()) // 4
                .andDo(document("post-create", // 5
                        requestFields( // 6
                                fieldWithPath("title").description("Post 제목"), // 7
                                fieldWithPath("content").description("Post 내용").optional() // 8
                        )
                ));
    }

}
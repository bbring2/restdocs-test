package com.example.restdocs.service;

import com.example.restdocs.entity.User;
import com.example.restdocs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException();
                });
    }

}

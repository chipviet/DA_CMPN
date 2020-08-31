package com.doan.cnpm.controller;

import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController (UserRepository userRepository) {
        this.userRepository = userRepository;
    };

    @GetMapping("/User")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}

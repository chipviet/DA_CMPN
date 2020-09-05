package com.doan.cnpm.controller;

import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.security.jwt.JWTFilter;
import com.doan.cnpm.security.jwt.TokenProvider;
import com.doan.cnpm.service.UserService;
import com.doan.cnpm.service.dto.LoginDTO;
import com.doan.cnpm.service.dto.RegisterUserDTO;
import com.doan.cnpm.service.exception.UserIsInactiveException;
import com.doan.cnpm.service.exception.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/edu")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/user/details")
    public  ResponseEntity<User> getUserDetails(HttpServletRequest request)
            throws UserNotFoundException, UserIsInactiveException {
        String username = request.getHeader("username");
        User userDetailsResp = userService.getUserDetails(username);

        return new ResponseEntity<>(userDetailsResp, HttpStatus.OK);
    }

}

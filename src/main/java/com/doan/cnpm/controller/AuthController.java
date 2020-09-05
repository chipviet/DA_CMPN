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
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final TokenProvider tokenProvider;

    private UserService userService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user/login")
    public ResponseEntity<JWTToken> login (@Valid @RequestBody LoginDTO dto){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate((authenticationToken));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication,false);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);
        return new ResponseEntity<>(new JWTToken(jwt),httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/v1/user/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterUserDTO dto) {
        userService.registerUser(dto);
    }

    @GetMapping("/v1/users/details")
    public  ResponseEntity<User> getUserDetails(HttpServletRequest request)
            throws UserNotFoundException, UserIsInactiveException {
        String username = request.getHeader("username");
        User userDetailsResp = userService.getUserDetails(username);

//        UserDetailsResp resp = new UserDetailsResp();
//
//        if(userDetailsResp != null){
//            resp.setFirstName(userDetailsResp.getFirstName());
//            resp.setLastName(userDetailsResp.getLastName());
//            resp.setEmail(userDetailsResp.getEmail());
//            resp.setAddress(userDetailsResp.getAddress());
//            resp.setStatus(userDetailsResp.getStatus());
//        }

        return new ResponseEntity<>(userDetailsResp, HttpStatus.OK);
    }


    class JWTToken {

        private String authToken;

        public JWTToken(String authToken) {
            this.authToken = authToken;
        }

        @JsonProperty("authToken")
        String getAuthToken() {
            return authToken;
        }

        void setAuthToken(String authToken) {
            this.authToken = authToken;
        }
    }

}

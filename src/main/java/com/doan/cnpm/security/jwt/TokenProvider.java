package com.doan.cnpm.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.UserAuthorityService;
import com.doan.cnpm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.*;


@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private UserRepository userRepository;

    private UserAuthorityService userAuthorityService;

    private long tokenValidityInMilliseconds = 60480000000L;

    private long tokenValidityInMillisecondsForRememberMe =60480000000L;


    public TokenProvider( ) {
    }


    public TokenProvider(UserRepository userRepository, UserAuthorityService userAuthorityService ) {
        this.userRepository = userRepository;
        this.userAuthorityService = userAuthorityService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserAuthorityService(UserAuthorityService userAuthorityService) {
        this.userAuthorityService= userAuthorityService;
    }


    public String createToken(Authentication authentication, boolean rememberMe) {
        System.out.println(authentication.getName());
        Optional<com.doan.cnpm.domain.User> user = userRepository.findOneByUsername(authentication.getName());

        String userId = String.valueOf(user.get().getId());
        String authorities = userAuthorityService.getAuthority(userId);
        System.out.println(authorities);
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, "ChipvietHandsome")
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("ChipvietHandsome")
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey("ChipvietHandsome").parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}

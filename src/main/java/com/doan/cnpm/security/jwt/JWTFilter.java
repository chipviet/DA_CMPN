package com.doan.cnpm.security.jwt;


import com.doan.cnpm.service.request.MutableHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private static final String EDU_URI = "/api/edu/";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String uri = httpServletRequest.getRequestURI();
        log.info("Request");
        log.info("Request URI: " + uri);
        String jwt = resolveToken(httpServletRequest);

        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (uri.startsWith(EDU_URI)) {
                MutableHttpServletRequest req = new MutableHttpServletRequest(httpServletRequest);
                User user = (User) authentication.getPrincipal();
                String username = user.getUsername();
                req.putHeader("username", user.getUsername());
                filterChain.doFilter(req, servletResponse);
                return;
            }
        } else {
            if (uri.startsWith(EDU_URI) && !uri.endsWith("/getToken")) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                if (!StringUtils.hasText(jwt)) {
                    log.info("Missing token in the request header");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "202.tokenismissing.message");
                } else {
                    log.info("Invalid JWT token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "200.invalidtoken.message");
                }

                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


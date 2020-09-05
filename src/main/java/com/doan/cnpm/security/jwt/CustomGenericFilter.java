package com.doan.cnpm.security.jwt;

import com.doan.cnpm.context.ApplicationContextHolder;
import com.doan.cnpm.context.TutorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CustomGenericFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(CustomGenericFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();

        log.info("do Filter");

        if (uri.startsWith("/api/auth") && uri.endsWith("user/getToken")) {
            TutorContext tutorContext = ApplicationContextHolder.getWalletContext();

            if (tutorContext == null) {
                log.debug("User context is null");
                tutorContext = new TutorContext();
            }

            tutorContext.setLogin(true);
            ApplicationContextHolder.setWalletContext(tutorContext);
        }
        log.info("It's not getToken API");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

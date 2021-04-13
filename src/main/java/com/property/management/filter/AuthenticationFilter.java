package com.property.management.filter;

import com.property.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.property.management.constant.Constants.API_KEY;
import static com.property.management.constant.Constants.ERR_UNAUTHORIZED;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = httpServletRequest.getHeader(API_KEY);
        if (StringUtils.isBlank(apiKey)) {
            throwUnauthorizedException(httpServletResponse);
            return;
        }
        log.info("Api Key: {}", apiKey);
        if (!userRepository.existsById(apiKey)) {
            throwUnauthorizedException(httpServletResponse);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void throwUnauthorizedException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.TEXT_PLAIN_VALUE);
        httpServletResponse.getWriter().write(ERR_UNAUTHORIZED);
    }
}

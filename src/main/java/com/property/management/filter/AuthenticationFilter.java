package com.property.management.filter;

import com.property.management.entity.Role;
import com.property.management.entity.User;
import com.property.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.property.management.constant.Constants.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private Predicate<Role> checkIfItsUser = role -> role.getName().compareTo(USER_ROLE) == 0;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !new AntPathMatcher().match("/api/v1/**", request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = httpServletRequest.getHeader(API_KEY);
        if (StringUtils.isBlank(apiKey)) {
            throwUnauthorizedException(httpServletResponse);
            return;
        }
        log.info("Api Key: {}", apiKey);
        Optional<User> user = userRepository.findByApiKey(apiKey);
        if (!user.isPresent()) {
            throwUnauthorizedException(httpServletResponse);
            return;
        }

        Set<Role> roles = user.get().getRoles();
        if (hasNoAccess(roles, httpServletRequest)) {
            throwForbiddenException(httpServletResponse);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void throwUnauthorizedException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.TEXT_PLAIN_VALUE);
        httpServletResponse.getWriter().write(ERR_UNAUTHORIZED);
    }

    private void throwForbiddenException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType(MediaType.TEXT_PLAIN_VALUE);
        httpServletResponse.getWriter().write(ERR_FORBIDDEN);
    }

    private Boolean hasNoAccess(Set<Role> roles, HttpServletRequest request) {
        return (roles.size() == 1 && roles.stream().anyMatch(checkIfItsUser)) &&
                StringUtils.isBlank(request.getParameter(PROPERTY_NAME_PARAM));
    }
}

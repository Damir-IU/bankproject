package com.project.bankproject.security;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.project.bankproject.domain.util.ApiConstants.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

/**
 * class JwtRequestFilter for filter handler all HTTP requests in application.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;


    /**
     * Method for create filter.
     *
     * @param request     - The request to process
     * @param response    - The response associated with the request
     * @param filterChain - Provides access to the next filter in the chain for this
     *                    filter to pass the request and response to for further
     *                    processing
     */
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        String token = null;
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            token = bearer.substring(7);
        }
        if (token != null) {
            String login = tokenService.validateTokenAndGetLogin(token);
            User target = userRepository
                    .findByLogin(login)
                    .orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " NOT FOUND"));
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(target,
                            null,
                            List.of(new SimpleGrantedAuthority(target.getRole().name())));
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}

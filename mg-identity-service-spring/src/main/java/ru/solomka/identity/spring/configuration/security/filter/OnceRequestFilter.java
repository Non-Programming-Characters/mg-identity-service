package ru.solomka.identity.spring.configuration.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.token.TokenEntity;
import ru.solomka.identity.token.TokenExtractor;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnceRequestFilter extends OncePerRequestFilter {

    @NonNull TokenExtractor tokenExtractor;
    @NonNull PrincipalService principalService;
    @NonNull UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String forwardedBy = request.getHeader("X-Forwarded-By");

        if (!forwardedBy.equals("gateway-service")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        if (request.getRequestURI().contains("/public/")) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenEntity tokenEntity = tokenExtractor.extract(request.getHeader("Authorization").split(" ")[1]);

        if(tokenEntity != null) {
            UserEntity userEntity = userService.getById(tokenEntity.getUserId());
            principalService.setPrincipal(PrincipalEntity.builder().id(userEntity.getId()).username(userEntity.getLogin()).build());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/public/docs");
    }
}
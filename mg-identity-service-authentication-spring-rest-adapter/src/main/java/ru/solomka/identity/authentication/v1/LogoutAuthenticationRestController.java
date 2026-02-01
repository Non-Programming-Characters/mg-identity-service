package ru.solomka.identity.authentication.v1;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.authentication.cqrs.LogoutCommand;
import ru.solomka.identity.authentication.response.LogoutResponse;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalEntity;

@RestController
@RequestMapping("/api/v1/identity/security/auth/logout")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutAuthenticationRestController {

    @NonNull CommandHandler<LogoutCommand, Boolean> logoutCommandHandler;

    @PostMapping(produces = "application/json")
    public ResponseEntity<LogoutResponse> logout(Authentication principal, HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("REFRESH_TOKEN", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/api/v1/identity")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        PrincipalEntity principalEntity = (PrincipalEntity) principal.getPrincipal();
        return ResponseEntity.ok(new LogoutResponse(logoutCommandHandler.handle(
                new LogoutCommand(principalEntity.getId())
        )));
    }
}
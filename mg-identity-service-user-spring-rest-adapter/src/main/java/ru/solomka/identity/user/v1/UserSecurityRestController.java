package ru.solomka.identity.user.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.user.request.RegistrationRequest;
import ru.solomka.identity.user.response.AuthenticationResponse;

@RestController("/v1/identity/security")
public class UserSecurityRestController {

    @PostMapping(value = "/authentication", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> authenticationUser(@RequestBody AuthenticationResponse authenticationResponse) {
        // TODO: Principal service & Permissions
        return ResponseEntity.ofNullable(null);
    }

    @PostMapping(value = "/registration", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> authenticationUser(@RequestBody RegistrationRequest authenticationResponse) {
        // TODO: Principal service & Permissions
        return ResponseEntity.ofNullable(null);
    }
}
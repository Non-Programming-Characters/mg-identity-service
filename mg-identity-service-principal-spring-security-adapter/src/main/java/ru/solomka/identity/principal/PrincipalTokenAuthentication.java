package ru.solomka.identity.principal;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrincipalTokenAuthentication extends AbstractAuthenticationToken {

    @NonNull PrincipalEntity principalEntity;

    public PrincipalTokenAuthentication(@NotNull PrincipalEntity principalEntity) {
        super(Collections.emptyList());
        this.principalEntity = principalEntity;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principalEntity;
    }
}

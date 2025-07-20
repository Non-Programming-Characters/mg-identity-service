package ru.solomka.identity.principal;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class PrincipalSpringSecurityAdapter implements PrincipalRepository {

    @Override
    public Optional<PrincipalEntity> findPrincipal() {
        return Optional.ofNullable((PrincipalEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public PrincipalEntity setPrincipal(PrincipalEntity principal) {
        PrincipalTokenAuthentication principalTokenAuthentication = new PrincipalTokenAuthentication(principal);
        SecurityContextHolder.getContext().setAuthentication(principalTokenAuthentication);
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}
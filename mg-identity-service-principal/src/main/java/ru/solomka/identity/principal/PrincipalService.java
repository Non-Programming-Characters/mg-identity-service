package ru.solomka.identity.principal;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrincipalService {

    @NonNull PrincipalRepository principalRepository;

    public Optional<PrincipalEntity> findPrincipal() {
        return principalRepository.findPrincipal();
    }

    public PrincipalEntity setPrincipal(@NonNull final PrincipalEntity principal) {
        return principalRepository.setPrincipal(principal);
    }

    public boolean isAuthenticated() {
        return principalRepository.isAuthenticated();
    }
}
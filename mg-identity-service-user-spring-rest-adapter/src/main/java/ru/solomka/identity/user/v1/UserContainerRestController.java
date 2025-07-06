package ru.solomka.identity.user.v1;


import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserEntityResponseUserEntityMapper;
import ru.solomka.identity.user.cqrs.query.GetUserByIdQuery;
import ru.solomka.identity.user.response.UserEntityResponse;

import java.util.UUID;

@RestController("/v1/identity/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserContainerRestController {

    @NonNull CommandHandler<GetUserByIdQuery, UserEntity> getUserByIdQueryHandler;
    @NonNull UserEntityResponseUserEntityMapper userEntityResponseUserEntityMapper;

    @GetMapping
    public ResponseEntity<UserEntityResponse> getUserById(@RequestParam UUID id) {
        UserEntity userEntity = getUserByIdQueryHandler.handle(new GetUserByIdQuery(id));
        return ResponseEntity.ok(userEntityResponseUserEntityMapper.mapToInfrastructure(userEntity));
    }

}

package ru.solomka.identity.user.v1;


import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.cqrs.query.GetUserByEmailQuery;
import ru.solomka.identity.user.cqrs.query.GetUserByIdQuery;
import ru.solomka.identity.user.cqrs.query.GetUserByLoginQuery;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/identity/container/search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserContainerRestController {

    @NonNull CommandHandler<GetUserByIdQuery, UserEntity> getUserByIdQueryHandler;
    @NonNull CommandHandler<GetUserByLoginQuery, UserEntity> getUserByLoginQueryHandler;
    @NonNull CommandHandler<GetUserByEmailQuery, UserEntity> getUserByEmailQueryHandler;

    @GetMapping(value ="/spec", produces = "application/json")
    public ResponseEntity<UserEntity> getUserById(@RequestParam("searchBy") String searchBy, @RequestParam("value") Object value) {

        switch (searchBy) {
            case "email" -> {
                UserEntity userEntity = getUserByEmailQueryHandler.handle(new GetUserByEmailQuery(String.valueOf(value)));
                return ResponseEntity.ok(userEntity);
            }
            case "login" -> {
                UserEntity userEntity = getUserByLoginQueryHandler.handle(new GetUserByLoginQuery(String.valueOf(value)));
                return ResponseEntity.ok(userEntity);
            }
            case "id" -> {
                UserEntity userEntity = getUserByIdQueryHandler.handle(new GetUserByIdQuery(UUID.fromString(String.valueOf(value))));
                return ResponseEntity.ok(userEntity);
            }
            default -> throw new IllegalArgumentException("Invalid search parameter");
        }
    }
}
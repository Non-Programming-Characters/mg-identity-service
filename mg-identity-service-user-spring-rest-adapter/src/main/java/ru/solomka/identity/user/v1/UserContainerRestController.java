package ru.solomka.identity.user.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.solomka.identity.user.response.UserSearchResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/identity/users/search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Search", description = "API for retrieving user information by different identifiers")
public class UserContainerRestController {

    @NonNull CommandHandler<GetUserByIdQuery, UserEntity> getUserByIdQueryHandler;
    @NonNull CommandHandler<GetUserByLoginQuery, UserEntity> getUserByLoginQueryHandler;
    @NonNull CommandHandler<GetUserByEmailQuery, UserEntity> getUserByEmailQueryHandler;

    @GetMapping(produces = "application/json")
    @Operation(
            summary = "Find user by identifier",
            description = """
            Retrieves user details by specifying the search field (`searchBy`) and its value (`value`).
            
            Supported search fields:
            - `id`: UUID of the user
            - `login`: unique username
            - `email`: user's email address
            """
    )
    @Parameter(
            name = "searchBy",
            description = "Field to search by: `id`, `login`, or `email`",
            example = "email",
            required = true
    )
    @Parameter(
            name = "value",
            description = "Value of the specified field",
            example = "user@example.com",
            required = true
    )
    @ApiResponse(
            responseCode = "200",
            description = "User found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSearchResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid `searchBy` parameter or malformed value (e.g. invalid UUID)", content = @Content)
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    public ResponseEntity<UserSearchResponse> getUserById(
            @RequestParam("searchBy") String searchBy,
            @RequestParam("value") String value
    ) {
        return switch (searchBy.toLowerCase()) {
            case "email" -> {
                UserEntity userEntity = getUserByEmailQueryHandler.handle(new GetUserByEmailQuery(value));
                yield ResponseEntity.ok(new UserSearchResponse(
                        userEntity.getId(), userEntity.getLogin(),
                        userEntity.getEmail(), userEntity.getCreatedAt()
                ));
            }
            case "login" -> {
                UserEntity userEntity = getUserByLoginQueryHandler.handle(new GetUserByLoginQuery(value));
                yield ResponseEntity.ok(new UserSearchResponse(
                        userEntity.getId(), userEntity.getLogin(),
                        userEntity.getEmail(), userEntity.getCreatedAt()
                ));
            }
            case "id" -> {
                UUID userId = UUID.fromString(value);
                UserEntity userEntity = getUserByIdQueryHandler.handle(new GetUserByIdQuery(userId));
                yield ResponseEntity.ok(new UserSearchResponse(
                        userEntity.getId(), userEntity.getLogin(),
                        userEntity.getEmail(), userEntity.getCreatedAt()
                ));
            }
            default -> throw new IllegalArgumentException("Invalid search parameter: " + searchBy);
        };
    }
}
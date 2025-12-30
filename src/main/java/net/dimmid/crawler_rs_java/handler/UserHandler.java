package net.dimmid.crawler_rs_java.handler;

import lombok.RequiredArgsConstructor;
import net.dimmid.crawler_rs_java.dto.UserCreateDTO;
import net.dimmid.crawler_rs_java.dto.UserRequestDTO;
import net.dimmid.crawler_rs_java.service.UserService;
import net.dimmid.crawler_rs_java.util.JWTUtil;
import net.dimmid.crawler_rs_java.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final ValidationUtil validator;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserCreateDTO.class)
                .flatMap(validator::validateObject)
                .flatMap(userService::createUser)
                .flatMap(userResponse -> ServerResponse.status(HttpStatus.CREATED)
                        .header("Authorization", jwtUtil.generateJWTToken(
                                userResponse.username()
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }

    public Mono<ServerResponse> authenticateUser(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .flatMap(validator::validateObject)
                .flatMap(userService::authenticate)
                .flatMap(userResponse -> ServerResponse.status(HttpStatus.CREATED)
                        .header("Authorization", jwtUtil.generateJWTToken(
                                userResponse.username()
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }
}

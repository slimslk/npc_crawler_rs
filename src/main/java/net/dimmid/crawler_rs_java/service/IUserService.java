package net.dimmid.crawler_rs_java.service;

import net.dimmid.crawler_rs_java.dto.UserCreateDTO;
import net.dimmid.crawler_rs_java.dto.UserRequestDTO;
import net.dimmid.crawler_rs_java.dto.UserResponseDTO;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserResponseDTO> createUser(UserCreateDTO userCreateDTO);
    Mono<UserResponseDTO> authenticate(UserRequestDTO userRequestDTO);
}

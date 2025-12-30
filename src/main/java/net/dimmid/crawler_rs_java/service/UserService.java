package net.dimmid.crawler_rs_java.service;

import lombok.RequiredArgsConstructor;
import net.dimmid.crawler_rs_java.dto.UserCreateDTO;
import net.dimmid.crawler_rs_java.dto.UserRequestDTO;
import net.dimmid.crawler_rs_java.dto.UserResponseDTO;
import net.dimmid.crawler_rs_java.entity.User;
import net.dimmid.crawler_rs_java.error.AuthenticationException;
import net.dimmid.crawler_rs_java.error.DuplicateDataException;
import net.dimmid.crawler_rs_java.error.NotFoundException;
import net.dimmid.crawler_rs_java.mapper.UserMapper;
import net.dimmid.crawler_rs_java.repository.UserRepository;
import net.dimmid.crawler_rs_java.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<UserResponseDTO> createUser(UserCreateDTO userCreateDTO) {

        return userRepository.existsUserByUsername(userCreateDTO.username())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DuplicateDataException("Username already exists"));
                    }
                    return Mono.fromCallable(() -> {
                                User user = userMapper.toEntity(userCreateDTO);
                                user.setPassword(PasswordEncoderUtil.encodePassword(user.getPassword()));
                                return user;
                            })
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(userRepository::save)
                            .map(userMapper::toDTO);

                });
    }

    @Override
    public Mono<UserResponseDTO> authenticate(UserRequestDTO userRequestDTO) {
        return userRepository
                .findByUsername(userRequestDTO.username())
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User %s not found", userRequestDTO.username()))))
                .filter(user -> PasswordEncoderUtil.validatePassword(userRequestDTO.password(), user.getPassword()))
                .switchIfEmpty(Mono.error(new AuthenticationException("Invalid username or password")))
                .map(userMapper::toDTO);
    }
}

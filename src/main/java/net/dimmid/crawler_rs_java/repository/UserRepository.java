package net.dimmid.crawler_rs_java.repository;

import net.dimmid.crawler_rs_java.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUsername(String username);
    Mono<Boolean> existsUserByUsername(String username);
}

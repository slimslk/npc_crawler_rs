package net.dimmid.crawler_rs_java.repository;

import net.dimmid.crawler_rs_java.entity.Character;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CharacterRepository extends ReactiveCrudRepository<Character, String> {
    Flux<Character> getCharactersByUserId(String userId);
}

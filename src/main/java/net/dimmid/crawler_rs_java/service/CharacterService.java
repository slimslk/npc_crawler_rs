package net.dimmid.crawler_rs_java.service;

import lombok.RequiredArgsConstructor;
import net.dimmid.crawler_rs_java.dto.CharacterResponseDTO;
import net.dimmid.crawler_rs_java.error.NotFoundException;
import net.dimmid.crawler_rs_java.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class CharacterService implements ICharacterService {
    private final CharacterRepository characterRepository;

    @Override
    public Flux<CharacterResponseDTO> getCharacters(String username) {
        return characterRepository.getCharactersByUserId(username)
                .map(character -> new CharacterResponseDTO(character.getName()));
    }
}

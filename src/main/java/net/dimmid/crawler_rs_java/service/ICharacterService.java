package net.dimmid.crawler_rs_java.service;

import net.dimmid.crawler_rs_java.dto.CharacterResponseDTO;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ICharacterService {
    Flux<CharacterResponseDTO> getCharacters(String username);
}

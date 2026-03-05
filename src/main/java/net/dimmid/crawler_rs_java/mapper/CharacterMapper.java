package net.dimmid.crawler_rs_java.mapper;

import net.dimmid.crawler_rs_java.dto.UserResponseDTO;
import net.dimmid.crawler_rs_java.entity.Character;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CharacterMapper {

    UserResponseDTO toDTO(Character character);
}

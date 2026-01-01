package net.dimmid.crawler_rs_java.mapper;

import net.dimmid.crawler_rs_java.dto.UserCreateDTO;
import net.dimmid.crawler_rs_java.dto.UserResponseDTO;
import net.dimmid.crawler_rs_java.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toEntity(UserCreateDTO userCreateDTO);

    UserResponseDTO toDTO(User user);
}

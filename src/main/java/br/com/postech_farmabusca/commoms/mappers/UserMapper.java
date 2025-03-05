package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.user.UserRequestDTO;
import br.com.postech_farmabusca.controller.dto.user.UserResponseDTO;
import br.com.postech_farmabusca.core.domain.User;
import br.com.postech_farmabusca.resources.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static br.com.postech_farmabusca.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponseDto(User usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(UserRequestDTO dto);

    User toDomain(UserEntity entity);

    UserResponseDTO toResponse(User domain);

    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    @Mapping(target = "updatedAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    UserEntity toEntity(User domain);

    @Mapping(target = "id", ignore = true)
    void updateUserFromRequest(UserRequestDTO request, @MappingTarget UserEntity user);
}

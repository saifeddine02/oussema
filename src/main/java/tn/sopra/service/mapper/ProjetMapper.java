package tn.sopra.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import tn.sopra.domain.Projet;
import tn.sopra.domain.UserSopra;
import tn.sopra.service.dto.ProjetDTO;
import tn.sopra.service.dto.UserSopraDTO;

/**
 * Mapper for the entity {@link Projet} and its DTO {@link ProjetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjetMapper extends EntityMapper<ProjetDTO, Projet> {
    @Mapping(target = "projectMenbers", source = "projectMenbers", qualifiedByName = "userSopraIdSet")
    ProjetDTO toDto(Projet s);

    @Mapping(target = "removeProjectMenbers", ignore = true)
    Projet toEntity(ProjetDTO projetDTO);

    @Named("userSopraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserSopraDTO toDtoUserSopraId(UserSopra userSopra);

    @Named("userSopraIdSet")
    default Set<UserSopraDTO> toDtoUserSopraIdSet(Set<UserSopra> userSopra) {
        return userSopra.stream().map(this::toDtoUserSopraId).collect(Collectors.toSet());
    }
}

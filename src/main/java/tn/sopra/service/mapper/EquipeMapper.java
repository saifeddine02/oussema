package tn.sopra.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import tn.sopra.domain.Equipe;
import tn.sopra.domain.UserSopra;
import tn.sopra.service.dto.EquipeDTO;
import tn.sopra.service.dto.UserSopraDTO;

/**
 * Mapper for the entity {@link Equipe} and its DTO {@link EquipeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipeMapper extends EntityMapper<EquipeDTO, Equipe> {
    @Mapping(target = "equipeusers", source = "equipeusers", qualifiedByName = "userSopraIdSet")
    EquipeDTO toDto(Equipe s);

    @Mapping(target = "removeEquipeuser", ignore = true)
    Equipe toEntity(EquipeDTO equipeDTO);

    @Named("userSopraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserSopraDTO toDtoUserSopraId(UserSopra userSopra);

    @Named("userSopraIdSet")
    default Set<UserSopraDTO> toDtoUserSopraIdSet(Set<UserSopra> userSopra) {
        return userSopra.stream().map(this::toDtoUserSopraId).collect(Collectors.toSet());
    }
}

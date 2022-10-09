package tn.sopra.service.mapper;

import org.mapstruct.*;
import tn.sopra.domain.Demande;
import tn.sopra.domain.UserSopra;
import tn.sopra.service.dto.DemandeDTO;
import tn.sopra.service.dto.UserSopraDTO;

/**
 * Mapper for the entity {@link Demande} and its DTO {@link DemandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeMapper extends EntityMapper<DemandeDTO, Demande> {
    @Mapping(target = "userSopra", source = "userSopra", qualifiedByName = "userSopraId")
    DemandeDTO toDto(Demande s);

    @Named("userSopraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserSopraDTO toDtoUserSopraId(UserSopra userSopra);
}

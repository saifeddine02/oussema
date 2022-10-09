package tn.sopra.service.mapper;

import org.mapstruct.*;
import tn.sopra.domain.Competance;
import tn.sopra.domain.UserSopra;
import tn.sopra.service.dto.CompetanceDTO;
import tn.sopra.service.dto.UserSopraDTO;

/**
 * Mapper for the entity {@link Competance} and its DTO {@link CompetanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetanceMapper extends EntityMapper<CompetanceDTO, Competance> {
    @Mapping(target = "userSopra", source = "userSopra", qualifiedByName = "userSopraId")
    CompetanceDTO toDto(Competance s);

    @Named("userSopraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserSopraDTO toDtoUserSopraId(UserSopra userSopra);
}

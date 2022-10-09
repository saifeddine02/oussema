package tn.sopra.service.mapper;

import org.mapstruct.*;
import tn.sopra.domain.Responsable;
import tn.sopra.domain.UserSopra;
import tn.sopra.service.dto.ResponsableDTO;
import tn.sopra.service.dto.UserSopraDTO;

/**
 * Mapper for the entity {@link UserSopra} and its DTO {@link UserSopraDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserSopraMapper extends EntityMapper<UserSopraDTO, UserSopra> {
    @Mapping(target = "responsable", source = "responsable", qualifiedByName = "responsableId")
    UserSopraDTO toDto(UserSopra s);

    @Named("responsableId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsableDTO toDtoResponsableId(Responsable responsable);
}

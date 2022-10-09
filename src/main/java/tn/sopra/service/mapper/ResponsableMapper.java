package tn.sopra.service.mapper;

import org.mapstruct.*;
import tn.sopra.domain.Responsable;
import tn.sopra.service.dto.ResponsableDTO;

/**
 * Mapper for the entity {@link Responsable} and its DTO {@link ResponsableDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsableMapper extends EntityMapper<ResponsableDTO, Responsable> {}

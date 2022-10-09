package tn.sopra.service.mapper;

import org.mapstruct.*;
import tn.sopra.domain.DemandeConge;
import tn.sopra.service.dto.DemandeCongeDTO;

/**
 * Mapper for the entity {@link DemandeConge} and its DTO {@link DemandeCongeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeCongeMapper extends EntityMapper<DemandeCongeDTO, DemandeConge> {}

package tn.sopra.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.domain.Responsable;
import tn.sopra.repository.ResponsableRepository;
import tn.sopra.service.ResponsableService;
import tn.sopra.service.dto.ResponsableDTO;
import tn.sopra.service.mapper.ResponsableMapper;

/**
 * Service Implementation for managing {@link Responsable}.
 */
@Service
@Transactional
public class ResponsableServiceImpl implements ResponsableService {

    private final Logger log = LoggerFactory.getLogger(ResponsableServiceImpl.class);

    private final ResponsableRepository responsableRepository;

    private final ResponsableMapper responsableMapper;

    public ResponsableServiceImpl(ResponsableRepository responsableRepository, ResponsableMapper responsableMapper) {
        this.responsableRepository = responsableRepository;
        this.responsableMapper = responsableMapper;
    }

    @Override
    public ResponsableDTO save(ResponsableDTO responsableDTO) {
        log.debug("Request to save Responsable : {}", responsableDTO);
        Responsable responsable = responsableMapper.toEntity(responsableDTO);
        responsable = responsableRepository.save(responsable);
        return responsableMapper.toDto(responsable);
    }

    @Override
    public ResponsableDTO update(ResponsableDTO responsableDTO) {
        log.debug("Request to update Responsable : {}", responsableDTO);
        Responsable responsable = responsableMapper.toEntity(responsableDTO);
        responsable = responsableRepository.save(responsable);
        return responsableMapper.toDto(responsable);
    }

    @Override
    public Optional<ResponsableDTO> partialUpdate(ResponsableDTO responsableDTO) {
        log.debug("Request to partially update Responsable : {}", responsableDTO);

        return responsableRepository
            .findById(responsableDTO.getId())
            .map(existingResponsable -> {
                responsableMapper.partialUpdate(existingResponsable, responsableDTO);

                return existingResponsable;
            })
            .map(responsableRepository::save)
            .map(responsableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsables");
        return responsableRepository.findAll(pageable).map(responsableMapper::toDto);
    }

    /**
     *  Get all the responsables where Responsable is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsableDTO> findAllWhereResponsableIsNull() {
        log.debug("Request to get all responsables where Responsable is null");
        return StreamSupport
            .stream(responsableRepository.findAll().spliterator(), false)
            .filter(responsable -> responsable.getResponsable() == null)
            .map(responsableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsableDTO> findOne(Long id) {
        log.debug("Request to get Responsable : {}", id);
        return responsableRepository.findById(id).map(responsableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responsable : {}", id);
        responsableRepository.deleteById(id);
    }
}

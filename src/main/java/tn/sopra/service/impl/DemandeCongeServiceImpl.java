package tn.sopra.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.domain.DemandeConge;
import tn.sopra.repository.DemandeCongeRepository;
import tn.sopra.service.DemandeCongeService;
import tn.sopra.service.dto.DemandeCongeDTO;
import tn.sopra.service.mapper.DemandeCongeMapper;

/**
 * Service Implementation for managing {@link DemandeConge}.
 */
@Service
@Transactional
public class DemandeCongeServiceImpl implements DemandeCongeService {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeServiceImpl.class);

    private final DemandeCongeRepository demandeCongeRepository;

    private final DemandeCongeMapper demandeCongeMapper;

    public DemandeCongeServiceImpl(DemandeCongeRepository demandeCongeRepository, DemandeCongeMapper demandeCongeMapper) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.demandeCongeMapper = demandeCongeMapper;
    }

    @Override
    public DemandeCongeDTO save(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to save DemandeConge : {}", demandeCongeDTO);
        DemandeConge demandeConge = demandeCongeMapper.toEntity(demandeCongeDTO);
        demandeConge = demandeCongeRepository.save(demandeConge);
        return demandeCongeMapper.toDto(demandeConge);
    }

    @Override
    public DemandeCongeDTO update(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to update DemandeConge : {}", demandeCongeDTO);
        DemandeConge demandeConge = demandeCongeMapper.toEntity(demandeCongeDTO);
        demandeConge = demandeCongeRepository.save(demandeConge);
        return demandeCongeMapper.toDto(demandeConge);
    }

    @Override
    public Optional<DemandeCongeDTO> partialUpdate(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to partially update DemandeConge : {}", demandeCongeDTO);

        return demandeCongeRepository
            .findById(demandeCongeDTO.getId())
            .map(existingDemandeConge -> {
                demandeCongeMapper.partialUpdate(existingDemandeConge, demandeCongeDTO);

                return existingDemandeConge;
            })
            .map(demandeCongeRepository::save)
            .map(demandeCongeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeCongeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        return demandeCongeRepository.findAll(pageable).map(demandeCongeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeCongeDTO> findOne(Long id) {
        log.debug("Request to get DemandeConge : {}", id);
        return demandeCongeRepository.findById(id).map(demandeCongeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeConge : {}", id);
        demandeCongeRepository.deleteById(id);
    }
}

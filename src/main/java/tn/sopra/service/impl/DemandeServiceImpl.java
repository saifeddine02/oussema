package tn.sopra.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.domain.Demande;
import tn.sopra.repository.DemandeRepository;
import tn.sopra.service.DemandeService;
import tn.sopra.service.dto.DemandeDTO;
import tn.sopra.service.mapper.DemandeMapper;

/**
 * Service Implementation for managing {@link Demande}.
 */
@Service
@Transactional
public class DemandeServiceImpl implements DemandeService {

    private final Logger log = LoggerFactory.getLogger(DemandeServiceImpl.class);

    private final DemandeRepository demandeRepository;

    private final DemandeMapper demandeMapper;

    public DemandeServiceImpl(DemandeRepository demandeRepository, DemandeMapper demandeMapper) {
        this.demandeRepository = demandeRepository;
        this.demandeMapper = demandeMapper;
    }

    @Override
    public DemandeDTO save(DemandeDTO demandeDTO) {
        log.debug("Request to save Demande : {}", demandeDTO);
        Demande demande = demandeMapper.toEntity(demandeDTO);
        demande = demandeRepository.save(demande);
        return demandeMapper.toDto(demande);
    }

    @Override
    public DemandeDTO update(DemandeDTO demandeDTO) {
        log.debug("Request to update Demande : {}", demandeDTO);
        Demande demande = demandeMapper.toEntity(demandeDTO);
        demande = demandeRepository.save(demande);
        return demandeMapper.toDto(demande);
    }

    @Override
    public Optional<DemandeDTO> partialUpdate(DemandeDTO demandeDTO) {
        log.debug("Request to partially update Demande : {}", demandeDTO);

        return demandeRepository
            .findById(demandeDTO.getId())
            .map(existingDemande -> {
                demandeMapper.partialUpdate(existingDemande, demandeDTO);

                return existingDemande;
            })
            .map(demandeRepository::save)
            .map(demandeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Demandes");
        return demandeRepository.findAll(pageable).map(demandeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeDTO> findOne(Long id) {
        log.debug("Request to get Demande : {}", id);
        return demandeRepository.findById(id).map(demandeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demande : {}", id);
        demandeRepository.deleteById(id);
    }
}

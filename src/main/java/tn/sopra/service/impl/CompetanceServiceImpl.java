package tn.sopra.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.domain.Competance;
import tn.sopra.repository.CompetanceRepository;
import tn.sopra.service.CompetanceService;
import tn.sopra.service.dto.CompetanceDTO;
import tn.sopra.service.mapper.CompetanceMapper;

/**
 * Service Implementation for managing {@link Competance}.
 */
@Service
@Transactional
public class CompetanceServiceImpl implements CompetanceService {

    private final Logger log = LoggerFactory.getLogger(CompetanceServiceImpl.class);

    private final CompetanceRepository competanceRepository;

    private final CompetanceMapper competanceMapper;

    public CompetanceServiceImpl(CompetanceRepository competanceRepository, CompetanceMapper competanceMapper) {
        this.competanceRepository = competanceRepository;
        this.competanceMapper = competanceMapper;
    }

    @Override
    public CompetanceDTO save(CompetanceDTO competanceDTO) {
        log.debug("Request to save Competance : {}", competanceDTO);
        Competance competance = competanceMapper.toEntity(competanceDTO);
        competance = competanceRepository.save(competance);
        return competanceMapper.toDto(competance);
    }

    @Override
    public CompetanceDTO update(CompetanceDTO competanceDTO) {
        log.debug("Request to update Competance : {}", competanceDTO);
        Competance competance = competanceMapper.toEntity(competanceDTO);
        competance = competanceRepository.save(competance);
        return competanceMapper.toDto(competance);
    }

    @Override
    public Optional<CompetanceDTO> partialUpdate(CompetanceDTO competanceDTO) {
        log.debug("Request to partially update Competance : {}", competanceDTO);

        return competanceRepository
            .findById(competanceDTO.getId())
            .map(existingCompetance -> {
                competanceMapper.partialUpdate(existingCompetance, competanceDTO);

                return existingCompetance;
            })
            .map(competanceRepository::save)
            .map(competanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competances");
        return competanceRepository.findAll(pageable).map(competanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetanceDTO> findOne(Long id) {
        log.debug("Request to get Competance : {}", id);
        return competanceRepository.findById(id).map(competanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competance : {}", id);
        competanceRepository.deleteById(id);
    }
}

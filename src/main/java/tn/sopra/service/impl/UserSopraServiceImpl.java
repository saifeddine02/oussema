package tn.sopra.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.domain.UserSopra;
import tn.sopra.repository.UserSopraRepository;
import tn.sopra.service.UserSopraService;
import tn.sopra.service.dto.UserSopraDTO;
import tn.sopra.service.mapper.UserSopraMapper;

/**
 * Service Implementation for managing {@link UserSopra}.
 */
@Service
@Transactional
public class UserSopraServiceImpl implements UserSopraService {

    private final Logger log = LoggerFactory.getLogger(UserSopraServiceImpl.class);

    private final UserSopraRepository userSopraRepository;

    private final UserSopraMapper userSopraMapper;

    public UserSopraServiceImpl(UserSopraRepository userSopraRepository, UserSopraMapper userSopraMapper) {
        this.userSopraRepository = userSopraRepository;
        this.userSopraMapper = userSopraMapper;
    }

    @Override
    public UserSopraDTO save(UserSopraDTO userSopraDTO) {
        log.debug("Request to save UserSopra : {}", userSopraDTO);
        UserSopra userSopra = userSopraMapper.toEntity(userSopraDTO);
        userSopra = userSopraRepository.save(userSopra);
        return userSopraMapper.toDto(userSopra);
    }

    @Override
    public UserSopraDTO update(UserSopraDTO userSopraDTO) {
        log.debug("Request to update UserSopra : {}", userSopraDTO);
        UserSopra userSopra = userSopraMapper.toEntity(userSopraDTO);
        userSopra = userSopraRepository.save(userSopra);
        return userSopraMapper.toDto(userSopra);
    }

    @Override
    public Optional<UserSopraDTO> partialUpdate(UserSopraDTO userSopraDTO) {
        log.debug("Request to partially update UserSopra : {}", userSopraDTO);

        return userSopraRepository
            .findById(userSopraDTO.getId())
            .map(existingUserSopra -> {
                userSopraMapper.partialUpdate(existingUserSopra, userSopraDTO);

                return existingUserSopra;
            })
            .map(userSopraRepository::save)
            .map(userSopraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSopraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSopras");
        return userSopraRepository.findAll(pageable).map(userSopraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserSopraDTO> findOne(Long id) {
        log.debug("Request to get UserSopra : {}", id);
        return userSopraRepository.findById(id).map(userSopraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserSopra : {}", id);
        userSopraRepository.deleteById(id);
    }
}

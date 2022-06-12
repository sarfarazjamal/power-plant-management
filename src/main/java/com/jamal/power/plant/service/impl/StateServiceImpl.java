package com.jamal.power.plant.service.impl;

import com.jamal.power.plant.service.StateService;
import com.jamal.power.plant.domain.State;
import com.jamal.power.plant.repository.StateRepository;
import com.jamal.power.plant.service.dto.StateDTO;
import com.jamal.power.plant.service.mapper.StateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link State}.
 */
@Service
@Transactional
public class StateServiceImpl implements StateService {

    private final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    public StateServiceImpl(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    @Override
    public StateDTO save(StateDTO stateDTO) {
        log.debug("Request to save State : {}", stateDTO);
        State state = stateMapper.toEntity(stateDTO);
        state = stateRepository.save(state);
        return stateMapper.toDto(state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StateDTO> findAll() {
        log.debug("Request to get all States");
        return stateRepository.findAll().stream()
            .map(stateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StateDTO> findOne(Long id) {
        log.debug("Request to get State : {}", id);
        return stateRepository.findById(id)
            .map(stateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);
        stateRepository.deleteById(id);
    }
}

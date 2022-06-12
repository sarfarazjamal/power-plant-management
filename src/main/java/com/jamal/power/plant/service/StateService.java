package com.jamal.power.plant.service;

import com.jamal.power.plant.service.dto.StateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jamal.power.plant.domain.State}.
 */
public interface StateService {

    /**
     * Save a state.
     *
     * @param stateDTO the entity to save.
     * @return the persisted entity.
     */
    StateDTO save(StateDTO stateDTO);

    /**
     * Get all the states.
     *
     * @return the list of entities.
     */
    List<StateDTO> findAll();


    /**
     * Get the "id" state.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateDTO> findOne(Long id);

    /**
     * Delete the "id" state.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

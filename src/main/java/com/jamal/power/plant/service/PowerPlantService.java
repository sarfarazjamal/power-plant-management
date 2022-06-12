package com.jamal.power.plant.service;

import com.jamal.power.plant.service.dto.PowerPlantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jamal.power.plant.domain.PowerPlant}.
 */
public interface PowerPlantService {

    /**
     * Save a powerPlant.
     *
     * @param powerPlantDTO the entity to save.
     * @return the persisted entity.
     */
    PowerPlantDTO save(PowerPlantDTO powerPlantDTO);

    /**
     * Get all the powerPlants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PowerPlantDTO> findAll(Pageable pageable);


    /**
     * Get the "id" powerPlant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PowerPlantDTO> findOne(Long id);

    /**
     * Delete the "id" powerPlant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    BigDecimal findPlantCapacityCount();
}

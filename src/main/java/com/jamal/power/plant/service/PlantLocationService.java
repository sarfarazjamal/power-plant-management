package com.jamal.power.plant.service;

import com.jamal.power.plant.service.dto.PlantLocationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jamal.power.plant.domain.PlantLocation}.
 */
public interface PlantLocationService {

    /**
     * Save a plantLocation.
     *
     * @param plantLocationDTO the entity to save.
     * @return the persisted entity.
     */
    PlantLocationDTO save(PlantLocationDTO plantLocationDTO);

    /**
     * Get all the plantLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlantLocationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" plantLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlantLocationDTO> findOne(Long id);

    /**
     * Delete the "id" plantLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

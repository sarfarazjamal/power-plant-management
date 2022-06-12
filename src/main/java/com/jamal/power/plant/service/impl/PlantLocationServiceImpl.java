package com.jamal.power.plant.service.impl;

import com.jamal.power.plant.service.PlantLocationService;
import com.jamal.power.plant.domain.PlantLocation;
import com.jamal.power.plant.repository.PlantLocationRepository;
import com.jamal.power.plant.service.dto.PlantLocationDTO;
import com.jamal.power.plant.service.mapper.PlantLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlantLocation}.
 */
@Service
@Transactional
public class PlantLocationServiceImpl implements PlantLocationService {

    private final Logger log = LoggerFactory.getLogger(PlantLocationServiceImpl.class);

    private final PlantLocationRepository plantLocationRepository;

    private final PlantLocationMapper plantLocationMapper;

    public PlantLocationServiceImpl(PlantLocationRepository plantLocationRepository, PlantLocationMapper plantLocationMapper) {
        this.plantLocationRepository = plantLocationRepository;
        this.plantLocationMapper = plantLocationMapper;
    }

    @Override
    public PlantLocationDTO save(PlantLocationDTO plantLocationDTO) {
        log.debug("Request to save PlantLocation : {}", plantLocationDTO);
        PlantLocation plantLocation = plantLocationMapper.toEntity(plantLocationDTO);
        plantLocation = plantLocationRepository.save(plantLocation);
        return plantLocationMapper.toDto(plantLocation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlantLocations");
        return plantLocationRepository.findAll(pageable)
            .map(plantLocationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlantLocationDTO> findOne(Long id) {
        log.debug("Request to get PlantLocation : {}", id);
        return plantLocationRepository.findById(id)
            .map(plantLocationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlantLocation : {}", id);
        plantLocationRepository.deleteById(id);
    }
}

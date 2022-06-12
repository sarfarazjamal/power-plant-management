package com.jamal.power.plant.service.impl;

import com.jamal.power.plant.service.PowerPlantService;
import com.jamal.power.plant.domain.PowerPlant;
import com.jamal.power.plant.repository.PowerPlantRepository;
import com.jamal.power.plant.service.dto.PowerPlantDTO;
import com.jamal.power.plant.service.mapper.PowerPlantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PowerPlant}.
 */
@Service
@Transactional
public class PowerPlantServiceImpl implements PowerPlantService {

    private final Logger log = LoggerFactory.getLogger(PowerPlantServiceImpl.class);

    private final PowerPlantRepository powerPlantRepository;

    private final PowerPlantMapper powerPlantMapper;

    public PowerPlantServiceImpl(PowerPlantRepository powerPlantRepository, PowerPlantMapper powerPlantMapper) {
        this.powerPlantRepository = powerPlantRepository;
        this.powerPlantMapper = powerPlantMapper;
    }

    @Override
    public PowerPlantDTO save(PowerPlantDTO powerPlantDTO) {
        log.debug("Request to save PowerPlant : {}", powerPlantDTO);
        PowerPlant powerPlant = powerPlantMapper.toEntity(powerPlantDTO);
        powerPlant = powerPlantRepository.save(powerPlant);
        return powerPlantMapper.toDto(powerPlant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PowerPlantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PowerPlants");
        return powerPlantRepository.findAll(pageable)
            .map(powerPlantMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PowerPlantDTO> findOne(Long id) {
        log.debug("Request to get PowerPlant : {}", id);
        return powerPlantRepository.findById(id)
            .map(powerPlantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PowerPlant : {}", id);
        powerPlantRepository.deleteById(id);
    }

    @Override
    public BigDecimal findPlantCapacityCount() {
        List<String> plantOutputList=  powerPlantRepository.findPowerPlantCapacityCount();
        List<BigDecimal> outputList=plantOutputList.stream().map(e->new BigDecimal(e)).collect(Collectors.toList());
        BigDecimal result = outputList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }
}

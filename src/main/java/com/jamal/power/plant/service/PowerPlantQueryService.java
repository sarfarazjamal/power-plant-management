package com.jamal.power.plant.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jamal.power.plant.domain.PowerPlant;
import com.jamal.power.plant.domain.*; // for static metamodels
import com.jamal.power.plant.repository.PowerPlantRepository;
import com.jamal.power.plant.service.dto.PowerPlantCriteria;
import com.jamal.power.plant.service.dto.PowerPlantDTO;
import com.jamal.power.plant.service.mapper.PowerPlantMapper;

/**
 * Service for executing complex queries for {@link PowerPlant} entities in the database.
 * The main input is a {@link PowerPlantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PowerPlantDTO} or a {@link Page} of {@link PowerPlantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PowerPlantQueryService extends QueryService<PowerPlant> {

    private final Logger log = LoggerFactory.getLogger(PowerPlantQueryService.class);

    private final PowerPlantRepository powerPlantRepository;

    private final PowerPlantMapper powerPlantMapper;

    public PowerPlantQueryService(PowerPlantRepository powerPlantRepository, PowerPlantMapper powerPlantMapper) {
        this.powerPlantRepository = powerPlantRepository;
        this.powerPlantMapper = powerPlantMapper;
    }

    /**
     * Return a {@link List} of {@link PowerPlantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PowerPlantDTO> findByCriteria(PowerPlantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PowerPlant> specification = createSpecification(criteria);
        return powerPlantMapper.toDto(powerPlantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PowerPlantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerPlantDTO> findByCriteria(PowerPlantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PowerPlant> specification = createSpecification(criteria);
        Page<PowerPlantDTO> pageResult=powerPlantRepository.findAll(specification, page).map(powerPlantMapper::toDto);
       // List<com.jamal.power.plant.service.dto.PowerPlantDTO> contentList=pageResult.getContent();
        //List<BigDecimal> outputList=contentList.stream().map(e->new BigDecimal(e.getPlantOutput())).collect(Collectors.toList());
        //BigDecimal result = outputList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return pageResult;
    }

    @Transactional(readOnly = true)
    public BigDecimal findByLocationCriteria(PowerPlantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PowerPlant> specification = createSpecification(criteria);
        Page<PowerPlantDTO> pageResult=powerPlantRepository.findAll(specification, page).map(powerPlantMapper::toDto);
        List<com.jamal.power.plant.service.dto.PowerPlantDTO> contentList=pageResult.getContent();
        List<BigDecimal> outputList=contentList.stream().map(e->new BigDecimal(e.getPlantOutput())).collect(Collectors.toList());
        BigDecimal result = outputList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PowerPlantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PowerPlant> specification = createSpecification(criteria);
        return powerPlantRepository.count(specification);
    }

    /**
     * Function to convert {@link PowerPlantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PowerPlant> createSpecification(PowerPlantCriteria criteria) {
        Specification<PowerPlant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PowerPlant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PowerPlant_.name));
            }
            if (criteria.getPlantOutput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlantOutput(), PowerPlant_.plantOutput));
            }
            if (criteria.getOutputUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutputUnit(), PowerPlant_.outputUnit));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), PowerPlant_.type));
            }
            if (criteria.getPlantManpowerCapacity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlantManpowerCapacity(), PowerPlant_.plantManpowerCapacity));
            }
            if (criteria.getWorkingHour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkingHour(), PowerPlant_.workingHour));
            }
            if (criteria.getPlantLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlantLocationId(),
                    root -> root.join(PowerPlant_.plantLocation, JoinType.LEFT).get(PlantLocation_.id)));
            }
        }
        return specification;
    }
}

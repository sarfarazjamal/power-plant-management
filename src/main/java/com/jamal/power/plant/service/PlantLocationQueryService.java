package com.jamal.power.plant.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jamal.power.plant.domain.PlantLocation;
import com.jamal.power.plant.domain.*; // for static metamodels
import com.jamal.power.plant.repository.PlantLocationRepository;
import com.jamal.power.plant.service.dto.PlantLocationCriteria;
import com.jamal.power.plant.service.dto.PlantLocationDTO;
import com.jamal.power.plant.service.mapper.PlantLocationMapper;

/**
 * Service for executing complex queries for {@link PlantLocation} entities in the database.
 * The main input is a {@link PlantLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlantLocationDTO} or a {@link Page} of {@link PlantLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlantLocationQueryService extends QueryService<PlantLocation> {

    private final Logger log = LoggerFactory.getLogger(PlantLocationQueryService.class);

    private final PlantLocationRepository plantLocationRepository;

    private final PlantLocationMapper plantLocationMapper;

    public PlantLocationQueryService(PlantLocationRepository plantLocationRepository, PlantLocationMapper plantLocationMapper) {
        this.plantLocationRepository = plantLocationRepository;
        this.plantLocationMapper = plantLocationMapper;
    }

    /**
     * Return a {@link List} of {@link PlantLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlantLocationDTO> findByCriteria(PlantLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlantLocation> specification = createSpecification(criteria);
        return plantLocationMapper.toDto(plantLocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlantLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlantLocationDTO> findByCriteria(PlantLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlantLocation> specification = createSpecification(criteria);
        return plantLocationRepository.findAll(specification, page)
            .map(plantLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlantLocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlantLocation> specification = createSpecification(criteria);
        return plantLocationRepository.count(specification);
    }

    /**
     * Function to convert {@link PlantLocationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlantLocation> createSpecification(PlantLocationCriteria criteria) {
        Specification<PlantLocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlantLocation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PlantLocation_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), PlantLocation_.type));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), PlantLocation_.address));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), PlantLocation_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), PlantLocation_.longitude));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), PlantLocation_.number));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), PlantLocation_.email));
            }
            if (criteria.getPowerPlantId() != null) {
                specification = specification.and(buildSpecification(criteria.getPowerPlantId(),
                    root -> root.join(PlantLocation_.powerPlants, JoinType.LEFT).get(PowerPlant_.id)));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(PlantLocation_.country, JoinType.LEFT).get(Country_.id)));
            }
            if (criteria.getStateId() != null) {
                specification = specification.and(buildSpecification(criteria.getStateId(),
                    root -> root.join(PlantLocation_.state, JoinType.LEFT).get(State_.id)));
            }
        }
        return specification;
    }
}

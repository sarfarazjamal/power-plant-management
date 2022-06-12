package com.jamal.power.plant.repository;

import com.jamal.power.plant.domain.PlantLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlantLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantLocationRepository extends JpaRepository<PlantLocation, Long>, JpaSpecificationExecutor<PlantLocation> {
}

package com.jamal.power.plant.repository;

import com.jamal.power.plant.domain.PowerPlant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring Data  repository for the PowerPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerPlantRepository extends JpaRepository<PowerPlant, Long>, JpaSpecificationExecutor<PowerPlant> {
    @Query("select e.plantOutput from PowerPlant e ")
    List<String> findPowerPlantCapacityCount();
}

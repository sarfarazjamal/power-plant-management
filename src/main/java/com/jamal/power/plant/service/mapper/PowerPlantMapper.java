package com.jamal.power.plant.service.mapper;


import com.jamal.power.plant.domain.*;
import com.jamal.power.plant.service.dto.PowerPlantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PowerPlant} and its DTO {@link PowerPlantDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlantLocationMapper.class})
public interface PowerPlantMapper extends EntityMapper<PowerPlantDTO, PowerPlant> {

    @Mapping(source = "plantLocation.id", target = "plantLocationId")
    PowerPlantDTO toDto(PowerPlant powerPlant);

    @Mapping(source = "plantLocationId", target = "plantLocation")
    PowerPlant toEntity(PowerPlantDTO powerPlantDTO);

    default PowerPlant fromId(Long id) {
        if (id == null) {
            return null;
        }
        PowerPlant powerPlant = new PowerPlant();
        powerPlant.setId(id);
        return powerPlant;
    }
}

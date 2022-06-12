package com.jamal.power.plant.service.mapper;


import com.jamal.power.plant.domain.*;
import com.jamal.power.plant.service.dto.PlantLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlantLocation} and its DTO {@link PlantLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, StateMapper.class})
public interface PlantLocationMapper extends EntityMapper<PlantLocationDTO, PlantLocation> {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "state.id", target = "stateId")
    PlantLocationDTO toDto(PlantLocation plantLocation);

    @Mapping(target = "powerPlants", ignore = true)
    @Mapping(target = "removePowerPlant", ignore = true)
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "stateId", target = "state")
    PlantLocation toEntity(PlantLocationDTO plantLocationDTO);

    default PlantLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlantLocation plantLocation = new PlantLocation();
        plantLocation.setId(id);
        return plantLocation;
    }
}

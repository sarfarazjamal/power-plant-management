package com.jamal.power.plant.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlantLocationMapperTest {

    private PlantLocationMapper plantLocationMapper;

    @BeforeEach
    public void setUp() {
        plantLocationMapper = new PlantLocationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(plantLocationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(plantLocationMapper.fromId(null)).isNull();
    }
}

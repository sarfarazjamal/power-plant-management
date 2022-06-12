package com.jamal.power.plant.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PowerPlantMapperTest {

    private PowerPlantMapper powerPlantMapper;

    @BeforeEach
    public void setUp() {
        powerPlantMapper = new PowerPlantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(powerPlantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(powerPlantMapper.fromId(null)).isNull();
    }
}

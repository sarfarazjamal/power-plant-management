package com.jamal.power.plant.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.power.plant.web.rest.TestUtil;

public class PowerPlantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerPlantDTO.class);
        PowerPlantDTO powerPlantDTO1 = new PowerPlantDTO();
        powerPlantDTO1.setId(1L);
        PowerPlantDTO powerPlantDTO2 = new PowerPlantDTO();
        assertThat(powerPlantDTO1).isNotEqualTo(powerPlantDTO2);
        powerPlantDTO2.setId(powerPlantDTO1.getId());
        assertThat(powerPlantDTO1).isEqualTo(powerPlantDTO2);
        powerPlantDTO2.setId(2L);
        assertThat(powerPlantDTO1).isNotEqualTo(powerPlantDTO2);
        powerPlantDTO1.setId(null);
        assertThat(powerPlantDTO1).isNotEqualTo(powerPlantDTO2);
    }
}

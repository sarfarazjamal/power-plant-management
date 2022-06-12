package com.jamal.power.plant.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.power.plant.web.rest.TestUtil;

public class PowerPlantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerPlant.class);
        PowerPlant powerPlant1 = new PowerPlant();
        powerPlant1.setId(1L);
        PowerPlant powerPlant2 = new PowerPlant();
        powerPlant2.setId(powerPlant1.getId());
        assertThat(powerPlant1).isEqualTo(powerPlant2);
        powerPlant2.setId(2L);
        assertThat(powerPlant1).isNotEqualTo(powerPlant2);
        powerPlant1.setId(null);
        assertThat(powerPlant1).isNotEqualTo(powerPlant2);
    }
}

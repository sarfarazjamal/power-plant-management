package com.jamal.power.plant.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.power.plant.web.rest.TestUtil;

public class PlantLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantLocation.class);
        PlantLocation plantLocation1 = new PlantLocation();
        plantLocation1.setId(1L);
        PlantLocation plantLocation2 = new PlantLocation();
        plantLocation2.setId(plantLocation1.getId());
        assertThat(plantLocation1).isEqualTo(plantLocation2);
        plantLocation2.setId(2L);
        assertThat(plantLocation1).isNotEqualTo(plantLocation2);
        plantLocation1.setId(null);
        assertThat(plantLocation1).isNotEqualTo(plantLocation2);
    }
}

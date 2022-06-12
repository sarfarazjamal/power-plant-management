package com.jamal.power.plant.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.power.plant.web.rest.TestUtil;

public class PlantLocationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantLocationDTO.class);
        PlantLocationDTO plantLocationDTO1 = new PlantLocationDTO();
        plantLocationDTO1.setId(1L);
        PlantLocationDTO plantLocationDTO2 = new PlantLocationDTO();
        assertThat(plantLocationDTO1).isNotEqualTo(plantLocationDTO2);
        plantLocationDTO2.setId(plantLocationDTO1.getId());
        assertThat(plantLocationDTO1).isEqualTo(plantLocationDTO2);
        plantLocationDTO2.setId(2L);
        assertThat(plantLocationDTO1).isNotEqualTo(plantLocationDTO2);
        plantLocationDTO1.setId(null);
        assertThat(plantLocationDTO1).isNotEqualTo(plantLocationDTO2);
    }
}

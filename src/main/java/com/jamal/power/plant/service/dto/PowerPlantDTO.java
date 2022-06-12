package com.jamal.power.plant.service.dto;

import java.io.Serializable;
import com.jamal.power.plant.domain.enumeration.PlantWorkingTypeEnum;

/**
 * A DTO for the {@link com.jamal.power.plant.domain.PowerPlant} entity.
 */
public class PowerPlantDTO implements Serializable {

    private Long id;

    private String name;

    private String plantOutput;

    private String outputUnit;

    private PlantWorkingTypeEnum type;

    private String plantManpowerCapacity;

    private String workingHour;

    private String actualValue;

    private String percentageValue;


    private Long plantLocationId;

    public PowerPlantDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlantOutput() {
        return plantOutput;
    }

    public void setPlantOutput(String plantOutput) {
        this.plantOutput = plantOutput;
    }

    public String getOutputUnit() {
        return outputUnit;
    }

    public void setOutputUnit(String outputUnit) {
        this.outputUnit = outputUnit;
    }

    public PlantWorkingTypeEnum getType() {
        return type;
    }

    public void setType(PlantWorkingTypeEnum type) {
        this.type = type;
    }

    public String getPlantManpowerCapacity() {
        return plantManpowerCapacity;
    }

    public void setPlantManpowerCapacity(String plantManpowerCapacity) {
        this.plantManpowerCapacity = plantManpowerCapacity;
    }

    public String getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(String workingHour) {
        this.workingHour = workingHour;
    }

    public Long getPlantLocationId() {
        return plantLocationId;
    }

    public void setPlantLocationId(Long plantLocationId) {
        this.plantLocationId = plantLocationId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerPlantDTO)) {
            return false;
        }

        return id != null && id.equals(((PowerPlantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerPlantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", plantOutput='" + getPlantOutput() + "'" +
            ", outputUnit='" + getOutputUnit() + "'" +
            ", type='" + getType() + "'" +
            ", plantManpowerCapacity='" + getPlantManpowerCapacity() + "'" +
            ", workingHour='" + getWorkingHour() + "'" +
            ", plantLocationId=" + getPlantLocationId() +
            "}";
    }

    public String getPercentageValue() {
        return percentageValue;
    }

    public void setPercentageValue(String percentageValue) {
        this.percentageValue = percentageValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }
}

package com.jamal.power.plant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import com.jamal.power.plant.domain.enumeration.PlantWorkingTypeEnum;

/**
 * A PowerPlant.
 */
@Entity
@Table(name = "power_plant")
public class PowerPlant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plant_output")
    private String plantOutput;

    @Column(name = "output_unit")
    private String outputUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PlantWorkingTypeEnum type;

    @Column(name = "plant_manpower_capacity")
    private String plantManpowerCapacity;

    @Column(name = "working_hour")
    private String workingHour;

    @ManyToOne
    @JoinColumn(name="plantLocation_id")
    // @JsonIgnoreProperties(value = "powerPlants", allowSetters = true)
    private PlantLocation plantLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PowerPlant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlantOutput() {
        return plantOutput;
    }

    public PowerPlant plantOutput(String plantOutput) {
        this.plantOutput = plantOutput;
        return this;
    }

    public void setPlantOutput(String plantOutput) {
        this.plantOutput = plantOutput;
    }

    public String getOutputUnit() {
        return outputUnit;
    }

    public PowerPlant outputUnit(String outputUnit) {
        this.outputUnit = outputUnit;
        return this;
    }

    public void setOutputUnit(String outputUnit) {
        this.outputUnit = outputUnit;
    }

    public PlantWorkingTypeEnum getType() {
        return type;
    }

    public PowerPlant type(PlantWorkingTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(PlantWorkingTypeEnum type) {
        this.type = type;
    }

    public String getPlantManpowerCapacity() {
        return plantManpowerCapacity;
    }

    public PowerPlant plantManpowerCapacity(String plantManpowerCapacity) {
        this.plantManpowerCapacity = plantManpowerCapacity;
        return this;
    }

    public void setPlantManpowerCapacity(String plantManpowerCapacity) {
        this.plantManpowerCapacity = plantManpowerCapacity;
    }

    public String getWorkingHour() {
        return workingHour;
    }

    public PowerPlant workingHour(String workingHour) {
        this.workingHour = workingHour;
        return this;
    }

    public void setWorkingHour(String workingHour) {
        this.workingHour = workingHour;
    }

    public PlantLocation getPlantLocation() {
        return plantLocation;
    }

    public PowerPlant plantLocation(PlantLocation plantLocation) {
        this.plantLocation = plantLocation;
        return this;
    }

    public void setPlantLocation(PlantLocation plantLocation) {
        this.plantLocation = plantLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerPlant)) {
            return false;
        }
        return id != null && id.equals(((PowerPlant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerPlant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", plantOutput='" + getPlantOutput() + "'" +
            ", outputUnit='" + getOutputUnit() + "'" +
            ", type='" + getType() + "'" +
            ", plantManpowerCapacity='" + getPlantManpowerCapacity() + "'" +
            ", workingHour='" + getWorkingHour() + "'" +
            "}";
    }
}

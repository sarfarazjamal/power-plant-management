package com.jamal.power.plant.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.jamal.power.plant.domain.enumeration.PlantWorkingTypeEnum;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.jamal.power.plant.domain.PowerPlant} entity. This class is used
 * in {@link com.jamal.power.plant.web.rest.PowerPlantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /power-plants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PowerPlantCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PlantWorkingTypeEnum
     */
    public static class PlantWorkingTypeEnumFilter extends Filter<PlantWorkingTypeEnum> {

        public PlantWorkingTypeEnumFilter() {
        }

        public PlantWorkingTypeEnumFilter(PlantWorkingTypeEnumFilter filter) {
            super(filter);
        }

        @Override
        public PlantWorkingTypeEnumFilter copy() {
            return new PlantWorkingTypeEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter plantOutput;

    private StringFilter outputUnit;

    private PlantWorkingTypeEnumFilter type;

    private StringFilter plantManpowerCapacity;

    private StringFilter workingHour;

    private LongFilter plantLocationId;

    public PowerPlantCriteria() {
    }

    public PowerPlantCriteria(PowerPlantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.plantOutput = other.plantOutput == null ? null : other.plantOutput.copy();
        this.outputUnit = other.outputUnit == null ? null : other.outputUnit.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.plantManpowerCapacity = other.plantManpowerCapacity == null ? null : other.plantManpowerCapacity.copy();
        this.workingHour = other.workingHour == null ? null : other.workingHour.copy();
        this.plantLocationId = other.plantLocationId == null ? null : other.plantLocationId.copy();
    }

    @Override
    public PowerPlantCriteria copy() {
        return new PowerPlantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPlantOutput() {
        return plantOutput;
    }

    public void setPlantOutput(StringFilter plantOutput) {
        this.plantOutput = plantOutput;
    }

    public StringFilter getOutputUnit() {
        return outputUnit;
    }

    public void setOutputUnit(StringFilter outputUnit) {
        this.outputUnit = outputUnit;
    }

    public PlantWorkingTypeEnumFilter getType() {
        return type;
    }

    public void setType(PlantWorkingTypeEnumFilter type) {
        this.type = type;
    }

    public StringFilter getPlantManpowerCapacity() {
        return plantManpowerCapacity;
    }

    public void setPlantManpowerCapacity(StringFilter plantManpowerCapacity) {
        this.plantManpowerCapacity = plantManpowerCapacity;
    }

    public StringFilter getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(StringFilter workingHour) {
        this.workingHour = workingHour;
    }

    public LongFilter getPlantLocationId() {
        return plantLocationId;
    }

    public void setPlantLocationId(LongFilter plantLocationId) {
        this.plantLocationId = plantLocationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PowerPlantCriteria that = (PowerPlantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(plantOutput, that.plantOutput) &&
            Objects.equals(outputUnit, that.outputUnit) &&
            Objects.equals(type, that.type) &&
            Objects.equals(plantManpowerCapacity, that.plantManpowerCapacity) &&
            Objects.equals(workingHour, that.workingHour) &&
            Objects.equals(plantLocationId, that.plantLocationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        plantOutput,
        outputUnit,
        type,
        plantManpowerCapacity,
        workingHour,
        plantLocationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerPlantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (plantOutput != null ? "plantOutput=" + plantOutput + ", " : "") +
                (outputUnit != null ? "outputUnit=" + outputUnit + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (plantManpowerCapacity != null ? "plantManpowerCapacity=" + plantManpowerCapacity + ", " : "") +
                (workingHour != null ? "workingHour=" + workingHour + ", " : "") +
                (plantLocationId != null ? "plantLocationId=" + plantLocationId + ", " : "") +
            "}";
    }

}

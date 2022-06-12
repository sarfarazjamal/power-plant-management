package com.jamal.power.plant.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.jamal.power.plant.domain.enumeration.ContactTypeEnum;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.jamal.power.plant.domain.PlantLocation} entity. This class is used
 * in {@link com.jamal.power.plant.web.rest.PlantLocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plant-locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlantLocationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ContactTypeEnum
     */
    public static class ContactTypeEnumFilter extends Filter<ContactTypeEnum> {

        public ContactTypeEnumFilter() {
        }

        public ContactTypeEnumFilter(ContactTypeEnumFilter filter) {
            super(filter);
        }

        @Override
        public ContactTypeEnumFilter copy() {
            return new ContactTypeEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ContactTypeEnumFilter type;

    private StringFilter address;

    private StringFilter latitude;

    private StringFilter longitude;

    private StringFilter number;

    private StringFilter email;

    private LongFilter powerPlantId;

    private LongFilter countryId;

    private LongFilter stateId;

    public PlantLocationCriteria() {
    }

    public PlantLocationCriteria(PlantLocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.powerPlantId = other.powerPlantId == null ? null : other.powerPlantId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
    }

    @Override
    public PlantLocationCriteria copy() {
        return new PlantLocationCriteria(this);
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

    public ContactTypeEnumFilter getType() {
        return type;
    }

    public void setType(ContactTypeEnumFilter type) {
        this.type = type;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getNumber() {
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getPowerPlantId() {
        return powerPlantId;
    }

    public void setPowerPlantId(LongFilter powerPlantId) {
        this.powerPlantId = powerPlantId;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getStateId() {
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlantLocationCriteria that = (PlantLocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(address, that.address) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(number, that.number) &&
            Objects.equals(email, that.email) &&
            Objects.equals(powerPlantId, that.powerPlantId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(stateId, that.stateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        type,
        address,
        latitude,
        longitude,
        number,
        email,
        powerPlantId,
        countryId,
        stateId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantLocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (powerPlantId != null ? "powerPlantId=" + powerPlantId + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
            "}";
    }

}

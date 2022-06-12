package com.jamal.power.plant.service.dto;

import java.io.Serializable;
import com.jamal.power.plant.domain.enumeration.ContactTypeEnum;

/**
 * A DTO for the {@link com.jamal.power.plant.domain.PlantLocation} entity.
 */
public class PlantLocationDTO implements Serializable {
    
    private Long id;

    private String name;

    private ContactTypeEnum type;

    private String address;

    private String latitude;

    private String longitude;

    private String number;

    private String email;


    private Long countryId;

    private Long stateId;
    
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

    public ContactTypeEnum getType() {
        return type;
    }

    public void setType(ContactTypeEnum type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantLocationDTO)) {
            return false;
        }

        return id != null && id.equals(((PlantLocationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantLocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", address='" + getAddress() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", number='" + getNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", countryId=" + getCountryId() +
            ", stateId=" + getStateId() +
            "}";
    }
}

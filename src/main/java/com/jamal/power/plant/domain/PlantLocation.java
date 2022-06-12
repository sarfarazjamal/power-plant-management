package com.jamal.power.plant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jamal.power.plant.domain.enumeration.ContactTypeEnum;

/**
 * A PlantLocation.
 */
@Entity
@Table(name = "plant_location")
public class PlantLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContactTypeEnum type;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "number")
    private String number;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "plantLocation",fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    @JsonManagedReference
    private Set<PowerPlant> powerPlants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "plantLocations", allowSetters = true)
    private Country country;

    @ManyToOne
    @JsonIgnoreProperties(value = "plantLocations", allowSetters = true)
    private State state;

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

    public PlantLocation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactTypeEnum getType() {
        return type;
    }

    public PlantLocation type(ContactTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(ContactTypeEnum type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public PlantLocation address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public PlantLocation latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public PlantLocation longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNumber() {
        return number;
    }

    public PlantLocation number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public PlantLocation email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<PowerPlant> getPowerPlants() {
        return powerPlants;
    }

    public PlantLocation powerPlants(Set<PowerPlant> powerPlants) {
        this.powerPlants = powerPlants;
        return this;
    }

    public PlantLocation addPowerPlant(PowerPlant powerPlant) {
        this.powerPlants.add(powerPlant);
        powerPlant.setPlantLocation(this);
        return this;
    }

    public PlantLocation removePowerPlant(PowerPlant powerPlant) {
        this.powerPlants.remove(powerPlant);
        powerPlant.setPlantLocation(null);
        return this;
    }

    public void setPowerPlants(Set<PowerPlant> powerPlants) {
        this.powerPlants = powerPlants;
    }

    public Country getCountry() {
        return country;
    }

    public PlantLocation country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public PlantLocation state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantLocation)) {
            return false;
        }
        return id != null && id.equals(((PlantLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantLocation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", address='" + getAddress() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", number='" + getNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}

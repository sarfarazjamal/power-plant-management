package com.jamal.power.plant.web.rest;

import com.jamal.power.plant.PowerPlantApp;
import com.jamal.power.plant.domain.PlantLocation;
import com.jamal.power.plant.domain.PowerPlant;
import com.jamal.power.plant.domain.Country;
import com.jamal.power.plant.domain.State;
import com.jamal.power.plant.repository.PlantLocationRepository;
import com.jamal.power.plant.service.PlantLocationService;
import com.jamal.power.plant.service.dto.PlantLocationDTO;
import com.jamal.power.plant.service.mapper.PlantLocationMapper;
import com.jamal.power.plant.service.dto.PlantLocationCriteria;
import com.jamal.power.plant.service.PlantLocationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jamal.power.plant.domain.enumeration.ContactTypeEnum;
/**
 * Integration tests for the {@link PlantLocationResource} REST controller.
 */
@SpringBootTest(classes = PowerPlantApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlantLocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ContactTypeEnum DEFAULT_TYPE = ContactTypeEnum.HOME;
    private static final ContactTypeEnum UPDATED_TYPE = ContactTypeEnum.BUSINESS;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PlantLocationRepository plantLocationRepository;

    @Autowired
    private PlantLocationMapper plantLocationMapper;

    @Autowired
    private PlantLocationService plantLocationService;

    @Autowired
    private PlantLocationQueryService plantLocationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantLocationMockMvc;

    private PlantLocation plantLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantLocation createEntity(EntityManager em) {
        PlantLocation plantLocation = new PlantLocation()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .address(DEFAULT_ADDRESS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .number(DEFAULT_NUMBER)
            .email(DEFAULT_EMAIL);
        return plantLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantLocation createUpdatedEntity(EntityManager em) {
        PlantLocation plantLocation = new PlantLocation()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .number(UPDATED_NUMBER)
            .email(UPDATED_EMAIL);
        return plantLocation;
    }

    @BeforeEach
    public void initTest() {
        plantLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlantLocation() throws Exception {
        int databaseSizeBeforeCreate = plantLocationRepository.findAll().size();
        // Create the PlantLocation
        PlantLocationDTO plantLocationDTO = plantLocationMapper.toDto(plantLocation);
        restPlantLocationMockMvc.perform(post("/api/plant-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the PlantLocation in the database
        List<PlantLocation> plantLocationList = plantLocationRepository.findAll();
        assertThat(plantLocationList).hasSize(databaseSizeBeforeCreate + 1);
        PlantLocation testPlantLocation = plantLocationList.get(plantLocationList.size() - 1);
        assertThat(testPlantLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlantLocation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPlantLocation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlantLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPlantLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPlantLocation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPlantLocation.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createPlantLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantLocationRepository.findAll().size();

        // Create the PlantLocation with an existing ID
        plantLocation.setId(1L);
        PlantLocationDTO plantLocationDTO = plantLocationMapper.toDto(plantLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantLocationMockMvc.perform(post("/api/plant-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlantLocation in the database
        List<PlantLocation> plantLocationList = plantLocationRepository.findAll();
        assertThat(plantLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlantLocations() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList
        restPlantLocationMockMvc.perform(get("/api/plant-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getPlantLocation() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get the plantLocation
        restPlantLocationMockMvc.perform(get("/api/plant-locations/{id}", plantLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }


    @Test
    @Transactional
    public void getPlantLocationsByIdFiltering() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        Long id = plantLocation.getId();

        defaultPlantLocationShouldBeFound("id.equals=" + id);
        defaultPlantLocationShouldNotBeFound("id.notEquals=" + id);

        defaultPlantLocationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlantLocationShouldNotBeFound("id.greaterThan=" + id);

        defaultPlantLocationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlantLocationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where name equals to DEFAULT_NAME
        defaultPlantLocationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the plantLocationList where name equals to UPDATED_NAME
        defaultPlantLocationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where name not equals to DEFAULT_NAME
        defaultPlantLocationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the plantLocationList where name not equals to UPDATED_NAME
        defaultPlantLocationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlantLocationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the plantLocationList where name equals to UPDATED_NAME
        defaultPlantLocationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where name is not null
        defaultPlantLocationShouldBeFound("name.specified=true");

        // Get all the plantLocationList where name is null
        defaultPlantLocationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantLocationsByNameContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where name contains DEFAULT_NAME
        defaultPlantLocationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the plantLocationList where name contains UPDATED_NAME
        defaultPlantLocationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where name does not contain DEFAULT_NAME
        defaultPlantLocationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the plantLocationList where name does not contain UPDATED_NAME
        defaultPlantLocationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where type equals to DEFAULT_TYPE
        defaultPlantLocationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the plantLocationList where type equals to UPDATED_TYPE
        defaultPlantLocationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where type not equals to DEFAULT_TYPE
        defaultPlantLocationShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the plantLocationList where type not equals to UPDATED_TYPE
        defaultPlantLocationShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPlantLocationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the plantLocationList where type equals to UPDATED_TYPE
        defaultPlantLocationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where type is not null
        defaultPlantLocationShouldBeFound("type.specified=true");

        // Get all the plantLocationList where type is null
        defaultPlantLocationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where address equals to DEFAULT_ADDRESS
        defaultPlantLocationShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the plantLocationList where address equals to UPDATED_ADDRESS
        defaultPlantLocationShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where address not equals to DEFAULT_ADDRESS
        defaultPlantLocationShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the plantLocationList where address not equals to UPDATED_ADDRESS
        defaultPlantLocationShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultPlantLocationShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the plantLocationList where address equals to UPDATED_ADDRESS
        defaultPlantLocationShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where address is not null
        defaultPlantLocationShouldBeFound("address.specified=true");

        // Get all the plantLocationList where address is null
        defaultPlantLocationShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantLocationsByAddressContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where address contains DEFAULT_ADDRESS
        defaultPlantLocationShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the plantLocationList where address contains UPDATED_ADDRESS
        defaultPlantLocationShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where address does not contain DEFAULT_ADDRESS
        defaultPlantLocationShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the plantLocationList where address does not contain UPDATED_ADDRESS
        defaultPlantLocationShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where latitude equals to DEFAULT_LATITUDE
        defaultPlantLocationShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the plantLocationList where latitude equals to UPDATED_LATITUDE
        defaultPlantLocationShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where latitude not equals to DEFAULT_LATITUDE
        defaultPlantLocationShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the plantLocationList where latitude not equals to UPDATED_LATITUDE
        defaultPlantLocationShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultPlantLocationShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the plantLocationList where latitude equals to UPDATED_LATITUDE
        defaultPlantLocationShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where latitude is not null
        defaultPlantLocationShouldBeFound("latitude.specified=true");

        // Get all the plantLocationList where latitude is null
        defaultPlantLocationShouldNotBeFound("latitude.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantLocationsByLatitudeContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where latitude contains DEFAULT_LATITUDE
        defaultPlantLocationShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

        // Get all the plantLocationList where latitude contains UPDATED_LATITUDE
        defaultPlantLocationShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where latitude does not contain DEFAULT_LATITUDE
        defaultPlantLocationShouldNotBeFound("latitude.doesNotContain=" + DEFAULT_LATITUDE);

        // Get all the plantLocationList where latitude does not contain UPDATED_LATITUDE
        defaultPlantLocationShouldBeFound("latitude.doesNotContain=" + UPDATED_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where longitude equals to DEFAULT_LONGITUDE
        defaultPlantLocationShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the plantLocationList where longitude equals to UPDATED_LONGITUDE
        defaultPlantLocationShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where longitude not equals to DEFAULT_LONGITUDE
        defaultPlantLocationShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the plantLocationList where longitude not equals to UPDATED_LONGITUDE
        defaultPlantLocationShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultPlantLocationShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the plantLocationList where longitude equals to UPDATED_LONGITUDE
        defaultPlantLocationShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where longitude is not null
        defaultPlantLocationShouldBeFound("longitude.specified=true");

        // Get all the plantLocationList where longitude is null
        defaultPlantLocationShouldNotBeFound("longitude.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantLocationsByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where longitude contains DEFAULT_LONGITUDE
        defaultPlantLocationShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the plantLocationList where longitude contains UPDATED_LONGITUDE
        defaultPlantLocationShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where longitude does not contain DEFAULT_LONGITUDE
        defaultPlantLocationShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the plantLocationList where longitude does not contain UPDATED_LONGITUDE
        defaultPlantLocationShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where number equals to DEFAULT_NUMBER
        defaultPlantLocationShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the plantLocationList where number equals to UPDATED_NUMBER
        defaultPlantLocationShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where number not equals to DEFAULT_NUMBER
        defaultPlantLocationShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the plantLocationList where number not equals to UPDATED_NUMBER
        defaultPlantLocationShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultPlantLocationShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the plantLocationList where number equals to UPDATED_NUMBER
        defaultPlantLocationShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where number is not null
        defaultPlantLocationShouldBeFound("number.specified=true");

        // Get all the plantLocationList where number is null
        defaultPlantLocationShouldNotBeFound("number.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantLocationsByNumberContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where number contains DEFAULT_NUMBER
        defaultPlantLocationShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the plantLocationList where number contains UPDATED_NUMBER
        defaultPlantLocationShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where number does not contain DEFAULT_NUMBER
        defaultPlantLocationShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the plantLocationList where number does not contain UPDATED_NUMBER
        defaultPlantLocationShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where email equals to DEFAULT_EMAIL
        defaultPlantLocationShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the plantLocationList where email equals to UPDATED_EMAIL
        defaultPlantLocationShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where email not equals to DEFAULT_EMAIL
        defaultPlantLocationShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the plantLocationList where email not equals to UPDATED_EMAIL
        defaultPlantLocationShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPlantLocationShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the plantLocationList where email equals to UPDATED_EMAIL
        defaultPlantLocationShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where email is not null
        defaultPlantLocationShouldBeFound("email.specified=true");

        // Get all the plantLocationList where email is null
        defaultPlantLocationShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlantLocationsByEmailContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where email contains DEFAULT_EMAIL
        defaultPlantLocationShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the plantLocationList where email contains UPDATED_EMAIL
        defaultPlantLocationShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPlantLocationsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        // Get all the plantLocationList where email does not contain DEFAULT_EMAIL
        defaultPlantLocationShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the plantLocationList where email does not contain UPDATED_EMAIL
        defaultPlantLocationShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByPowerPlantIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);
        PowerPlant powerPlant = PowerPlantResourceIT.createEntity(em);
        em.persist(powerPlant);
        em.flush();
        plantLocation.addPowerPlant(powerPlant);
        plantLocationRepository.saveAndFlush(plantLocation);
        Long powerPlantId = powerPlant.getId();

        // Get all the plantLocationList where powerPlant equals to powerPlantId
        defaultPlantLocationShouldBeFound("powerPlantId.equals=" + powerPlantId);

        // Get all the plantLocationList where powerPlant equals to powerPlantId + 1
        defaultPlantLocationShouldNotBeFound("powerPlantId.equals=" + (powerPlantId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);
        Country country = CountryResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        plantLocation.setCountry(country);
        plantLocationRepository.saveAndFlush(plantLocation);
        Long countryId = country.getId();

        // Get all the plantLocationList where country equals to countryId
        defaultPlantLocationShouldBeFound("countryId.equals=" + countryId);

        // Get all the plantLocationList where country equals to countryId + 1
        defaultPlantLocationShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantLocationsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);
        State state = StateResourceIT.createEntity(em);
        em.persist(state);
        em.flush();
        plantLocation.setState(state);
        plantLocationRepository.saveAndFlush(plantLocation);
        Long stateId = state.getId();

        // Get all the plantLocationList where state equals to stateId
        defaultPlantLocationShouldBeFound("stateId.equals=" + stateId);

        // Get all the plantLocationList where state equals to stateId + 1
        defaultPlantLocationShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlantLocationShouldBeFound(String filter) throws Exception {
        restPlantLocationMockMvc.perform(get("/api/plant-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restPlantLocationMockMvc.perform(get("/api/plant-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlantLocationShouldNotBeFound(String filter) throws Exception {
        restPlantLocationMockMvc.perform(get("/api/plant-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlantLocationMockMvc.perform(get("/api/plant-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlantLocation() throws Exception {
        // Get the plantLocation
        restPlantLocationMockMvc.perform(get("/api/plant-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantLocation() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        int databaseSizeBeforeUpdate = plantLocationRepository.findAll().size();

        // Update the plantLocation
        PlantLocation updatedPlantLocation = plantLocationRepository.findById(plantLocation.getId()).get();
        // Disconnect from session so that the updates on updatedPlantLocation are not directly saved in db
        em.detach(updatedPlantLocation);
        updatedPlantLocation
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .number(UPDATED_NUMBER)
            .email(UPDATED_EMAIL);
        PlantLocationDTO plantLocationDTO = plantLocationMapper.toDto(updatedPlantLocation);

        restPlantLocationMockMvc.perform(put("/api/plant-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantLocationDTO)))
            .andExpect(status().isOk());

        // Validate the PlantLocation in the database
        List<PlantLocation> plantLocationList = plantLocationRepository.findAll();
        assertThat(plantLocationList).hasSize(databaseSizeBeforeUpdate);
        PlantLocation testPlantLocation = plantLocationList.get(plantLocationList.size() - 1);
        assertThat(testPlantLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlantLocation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlantLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlantLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPlantLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPlantLocation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPlantLocation.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPlantLocation() throws Exception {
        int databaseSizeBeforeUpdate = plantLocationRepository.findAll().size();

        // Create the PlantLocation
        PlantLocationDTO plantLocationDTO = plantLocationMapper.toDto(plantLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantLocationMockMvc.perform(put("/api/plant-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlantLocation in the database
        List<PlantLocation> plantLocationList = plantLocationRepository.findAll();
        assertThat(plantLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlantLocation() throws Exception {
        // Initialize the database
        plantLocationRepository.saveAndFlush(plantLocation);

        int databaseSizeBeforeDelete = plantLocationRepository.findAll().size();

        // Delete the plantLocation
        restPlantLocationMockMvc.perform(delete("/api/plant-locations/{id}", plantLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlantLocation> plantLocationList = plantLocationRepository.findAll();
        assertThat(plantLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.jamal.power.plant.web.rest;

import com.jamal.power.plant.PowerPlantApp;
import com.jamal.power.plant.domain.PowerPlant;
import com.jamal.power.plant.domain.PlantLocation;
import com.jamal.power.plant.repository.PowerPlantRepository;
import com.jamal.power.plant.service.PowerPlantService;
import com.jamal.power.plant.service.dto.PowerPlantDTO;
import com.jamal.power.plant.service.mapper.PowerPlantMapper;
import com.jamal.power.plant.service.dto.PowerPlantCriteria;
import com.jamal.power.plant.service.PowerPlantQueryService;

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

import com.jamal.power.plant.domain.enumeration.PlantWorkingTypeEnum;
/**
 * Integration tests for the {@link PowerPlantResource} REST controller.
 */
@SpringBootTest(classes = PowerPlantApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PowerPlantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_OUTPUT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT_UNIT = "BBBBBBBBBB";

    private static final PlantWorkingTypeEnum DEFAULT_TYPE = PlantWorkingTypeEnum.FUNCTIONAL;
    private static final PlantWorkingTypeEnum UPDATED_TYPE = PlantWorkingTypeEnum.INPROGRESS;

    private static final String DEFAULT_PLANT_MANPOWER_CAPACITY = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_MANPOWER_CAPACITY = "BBBBBBBBBB";

    private static final String DEFAULT_WORKING_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_WORKING_HOUR = "BBBBBBBBBB";

    @Autowired
    private PowerPlantRepository powerPlantRepository;

    @Autowired
    private PowerPlantMapper powerPlantMapper;

    @Autowired
    private PowerPlantService powerPlantService;

    @Autowired
    private PowerPlantQueryService powerPlantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerPlantMockMvc;

    private PowerPlant powerPlant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerPlant createEntity(EntityManager em) {
        PowerPlant powerPlant = new PowerPlant()
            .name(DEFAULT_NAME)
            .plantOutput(DEFAULT_PLANT_OUTPUT)
            .outputUnit(DEFAULT_OUTPUT_UNIT)
            .type(DEFAULT_TYPE)
            .plantManpowerCapacity(DEFAULT_PLANT_MANPOWER_CAPACITY)
            .workingHour(DEFAULT_WORKING_HOUR);
        return powerPlant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerPlant createUpdatedEntity(EntityManager em) {
        PowerPlant powerPlant = new PowerPlant()
            .name(UPDATED_NAME)
            .plantOutput(UPDATED_PLANT_OUTPUT)
            .outputUnit(UPDATED_OUTPUT_UNIT)
            .type(UPDATED_TYPE)
            .plantManpowerCapacity(UPDATED_PLANT_MANPOWER_CAPACITY)
            .workingHour(UPDATED_WORKING_HOUR);
        return powerPlant;
    }

    @BeforeEach
    public void initTest() {
        powerPlant = createEntity(em);
    }

    @Test
    @Transactional
    public void createPowerPlant() throws Exception {
        int databaseSizeBeforeCreate = powerPlantRepository.findAll().size();
        // Create the PowerPlant
        PowerPlantDTO powerPlantDTO = powerPlantMapper.toDto(powerPlant);
        restPowerPlantMockMvc.perform(post("/api/power-plants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerPlantDTO)))
            .andExpect(status().isCreated());

        // Validate the PowerPlant in the database
        List<PowerPlant> powerPlantList = powerPlantRepository.findAll();
        assertThat(powerPlantList).hasSize(databaseSizeBeforeCreate + 1);
        PowerPlant testPowerPlant = powerPlantList.get(powerPlantList.size() - 1);
        assertThat(testPowerPlant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPowerPlant.getPlantOutput()).isEqualTo(DEFAULT_PLANT_OUTPUT);
        assertThat(testPowerPlant.getOutputUnit()).isEqualTo(DEFAULT_OUTPUT_UNIT);
        assertThat(testPowerPlant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPowerPlant.getPlantManpowerCapacity()).isEqualTo(DEFAULT_PLANT_MANPOWER_CAPACITY);
        assertThat(testPowerPlant.getWorkingHour()).isEqualTo(DEFAULT_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void createPowerPlantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = powerPlantRepository.findAll().size();

        // Create the PowerPlant with an existing ID
        powerPlant.setId(1L);
        PowerPlantDTO powerPlantDTO = powerPlantMapper.toDto(powerPlant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerPlantMockMvc.perform(post("/api/power-plants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerPlantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PowerPlant in the database
        List<PowerPlant> powerPlantList = powerPlantRepository.findAll();
        assertThat(powerPlantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPowerPlants() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList
        restPowerPlantMockMvc.perform(get("/api/power-plants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].plantOutput").value(hasItem(DEFAULT_PLANT_OUTPUT)))
            .andExpect(jsonPath("$.[*].outputUnit").value(hasItem(DEFAULT_OUTPUT_UNIT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].plantManpowerCapacity").value(hasItem(DEFAULT_PLANT_MANPOWER_CAPACITY)))
            .andExpect(jsonPath("$.[*].workingHour").value(hasItem(DEFAULT_WORKING_HOUR)));
    }
    
    @Test
    @Transactional
    public void getPowerPlant() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get the powerPlant
        restPowerPlantMockMvc.perform(get("/api/power-plants/{id}", powerPlant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(powerPlant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.plantOutput").value(DEFAULT_PLANT_OUTPUT))
            .andExpect(jsonPath("$.outputUnit").value(DEFAULT_OUTPUT_UNIT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.plantManpowerCapacity").value(DEFAULT_PLANT_MANPOWER_CAPACITY))
            .andExpect(jsonPath("$.workingHour").value(DEFAULT_WORKING_HOUR));
    }


    @Test
    @Transactional
    public void getPowerPlantsByIdFiltering() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        Long id = powerPlant.getId();

        defaultPowerPlantShouldBeFound("id.equals=" + id);
        defaultPowerPlantShouldNotBeFound("id.notEquals=" + id);

        defaultPowerPlantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPowerPlantShouldNotBeFound("id.greaterThan=" + id);

        defaultPowerPlantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPowerPlantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPowerPlantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where name equals to DEFAULT_NAME
        defaultPowerPlantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the powerPlantList where name equals to UPDATED_NAME
        defaultPowerPlantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where name not equals to DEFAULT_NAME
        defaultPowerPlantShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the powerPlantList where name not equals to UPDATED_NAME
        defaultPowerPlantShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPowerPlantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the powerPlantList where name equals to UPDATED_NAME
        defaultPowerPlantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where name is not null
        defaultPowerPlantShouldBeFound("name.specified=true");

        // Get all the powerPlantList where name is null
        defaultPowerPlantShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerPlantsByNameContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where name contains DEFAULT_NAME
        defaultPowerPlantShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the powerPlantList where name contains UPDATED_NAME
        defaultPowerPlantShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where name does not contain DEFAULT_NAME
        defaultPowerPlantShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the powerPlantList where name does not contain UPDATED_NAME
        defaultPowerPlantShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPowerPlantsByPlantOutputIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantOutput equals to DEFAULT_PLANT_OUTPUT
        defaultPowerPlantShouldBeFound("plantOutput.equals=" + DEFAULT_PLANT_OUTPUT);

        // Get all the powerPlantList where plantOutput equals to UPDATED_PLANT_OUTPUT
        defaultPowerPlantShouldNotBeFound("plantOutput.equals=" + UPDATED_PLANT_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantOutputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantOutput not equals to DEFAULT_PLANT_OUTPUT
        defaultPowerPlantShouldNotBeFound("plantOutput.notEquals=" + DEFAULT_PLANT_OUTPUT);

        // Get all the powerPlantList where plantOutput not equals to UPDATED_PLANT_OUTPUT
        defaultPowerPlantShouldBeFound("plantOutput.notEquals=" + UPDATED_PLANT_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantOutputIsInShouldWork() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantOutput in DEFAULT_PLANT_OUTPUT or UPDATED_PLANT_OUTPUT
        defaultPowerPlantShouldBeFound("plantOutput.in=" + DEFAULT_PLANT_OUTPUT + "," + UPDATED_PLANT_OUTPUT);

        // Get all the powerPlantList where plantOutput equals to UPDATED_PLANT_OUTPUT
        defaultPowerPlantShouldNotBeFound("plantOutput.in=" + UPDATED_PLANT_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantOutputIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantOutput is not null
        defaultPowerPlantShouldBeFound("plantOutput.specified=true");

        // Get all the powerPlantList where plantOutput is null
        defaultPowerPlantShouldNotBeFound("plantOutput.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerPlantsByPlantOutputContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantOutput contains DEFAULT_PLANT_OUTPUT
        defaultPowerPlantShouldBeFound("plantOutput.contains=" + DEFAULT_PLANT_OUTPUT);

        // Get all the powerPlantList where plantOutput contains UPDATED_PLANT_OUTPUT
        defaultPowerPlantShouldNotBeFound("plantOutput.contains=" + UPDATED_PLANT_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantOutputNotContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantOutput does not contain DEFAULT_PLANT_OUTPUT
        defaultPowerPlantShouldNotBeFound("plantOutput.doesNotContain=" + DEFAULT_PLANT_OUTPUT);

        // Get all the powerPlantList where plantOutput does not contain UPDATED_PLANT_OUTPUT
        defaultPowerPlantShouldBeFound("plantOutput.doesNotContain=" + UPDATED_PLANT_OUTPUT);
    }


    @Test
    @Transactional
    public void getAllPowerPlantsByOutputUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where outputUnit equals to DEFAULT_OUTPUT_UNIT
        defaultPowerPlantShouldBeFound("outputUnit.equals=" + DEFAULT_OUTPUT_UNIT);

        // Get all the powerPlantList where outputUnit equals to UPDATED_OUTPUT_UNIT
        defaultPowerPlantShouldNotBeFound("outputUnit.equals=" + UPDATED_OUTPUT_UNIT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByOutputUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where outputUnit not equals to DEFAULT_OUTPUT_UNIT
        defaultPowerPlantShouldNotBeFound("outputUnit.notEquals=" + DEFAULT_OUTPUT_UNIT);

        // Get all the powerPlantList where outputUnit not equals to UPDATED_OUTPUT_UNIT
        defaultPowerPlantShouldBeFound("outputUnit.notEquals=" + UPDATED_OUTPUT_UNIT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByOutputUnitIsInShouldWork() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where outputUnit in DEFAULT_OUTPUT_UNIT or UPDATED_OUTPUT_UNIT
        defaultPowerPlantShouldBeFound("outputUnit.in=" + DEFAULT_OUTPUT_UNIT + "," + UPDATED_OUTPUT_UNIT);

        // Get all the powerPlantList where outputUnit equals to UPDATED_OUTPUT_UNIT
        defaultPowerPlantShouldNotBeFound("outputUnit.in=" + UPDATED_OUTPUT_UNIT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByOutputUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where outputUnit is not null
        defaultPowerPlantShouldBeFound("outputUnit.specified=true");

        // Get all the powerPlantList where outputUnit is null
        defaultPowerPlantShouldNotBeFound("outputUnit.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerPlantsByOutputUnitContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where outputUnit contains DEFAULT_OUTPUT_UNIT
        defaultPowerPlantShouldBeFound("outputUnit.contains=" + DEFAULT_OUTPUT_UNIT);

        // Get all the powerPlantList where outputUnit contains UPDATED_OUTPUT_UNIT
        defaultPowerPlantShouldNotBeFound("outputUnit.contains=" + UPDATED_OUTPUT_UNIT);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByOutputUnitNotContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where outputUnit does not contain DEFAULT_OUTPUT_UNIT
        defaultPowerPlantShouldNotBeFound("outputUnit.doesNotContain=" + DEFAULT_OUTPUT_UNIT);

        // Get all the powerPlantList where outputUnit does not contain UPDATED_OUTPUT_UNIT
        defaultPowerPlantShouldBeFound("outputUnit.doesNotContain=" + UPDATED_OUTPUT_UNIT);
    }


    @Test
    @Transactional
    public void getAllPowerPlantsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where type equals to DEFAULT_TYPE
        defaultPowerPlantShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the powerPlantList where type equals to UPDATED_TYPE
        defaultPowerPlantShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where type not equals to DEFAULT_TYPE
        defaultPowerPlantShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the powerPlantList where type not equals to UPDATED_TYPE
        defaultPowerPlantShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPowerPlantShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the powerPlantList where type equals to UPDATED_TYPE
        defaultPowerPlantShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where type is not null
        defaultPowerPlantShouldBeFound("type.specified=true");

        // Get all the powerPlantList where type is null
        defaultPowerPlantShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantManpowerCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantManpowerCapacity equals to DEFAULT_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldBeFound("plantManpowerCapacity.equals=" + DEFAULT_PLANT_MANPOWER_CAPACITY);

        // Get all the powerPlantList where plantManpowerCapacity equals to UPDATED_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldNotBeFound("plantManpowerCapacity.equals=" + UPDATED_PLANT_MANPOWER_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantManpowerCapacityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantManpowerCapacity not equals to DEFAULT_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldNotBeFound("plantManpowerCapacity.notEquals=" + DEFAULT_PLANT_MANPOWER_CAPACITY);

        // Get all the powerPlantList where plantManpowerCapacity not equals to UPDATED_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldBeFound("plantManpowerCapacity.notEquals=" + UPDATED_PLANT_MANPOWER_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantManpowerCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantManpowerCapacity in DEFAULT_PLANT_MANPOWER_CAPACITY or UPDATED_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldBeFound("plantManpowerCapacity.in=" + DEFAULT_PLANT_MANPOWER_CAPACITY + "," + UPDATED_PLANT_MANPOWER_CAPACITY);

        // Get all the powerPlantList where plantManpowerCapacity equals to UPDATED_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldNotBeFound("plantManpowerCapacity.in=" + UPDATED_PLANT_MANPOWER_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantManpowerCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantManpowerCapacity is not null
        defaultPowerPlantShouldBeFound("plantManpowerCapacity.specified=true");

        // Get all the powerPlantList where plantManpowerCapacity is null
        defaultPowerPlantShouldNotBeFound("plantManpowerCapacity.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerPlantsByPlantManpowerCapacityContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantManpowerCapacity contains DEFAULT_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldBeFound("plantManpowerCapacity.contains=" + DEFAULT_PLANT_MANPOWER_CAPACITY);

        // Get all the powerPlantList where plantManpowerCapacity contains UPDATED_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldNotBeFound("plantManpowerCapacity.contains=" + UPDATED_PLANT_MANPOWER_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByPlantManpowerCapacityNotContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where plantManpowerCapacity does not contain DEFAULT_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldNotBeFound("plantManpowerCapacity.doesNotContain=" + DEFAULT_PLANT_MANPOWER_CAPACITY);

        // Get all the powerPlantList where plantManpowerCapacity does not contain UPDATED_PLANT_MANPOWER_CAPACITY
        defaultPowerPlantShouldBeFound("plantManpowerCapacity.doesNotContain=" + UPDATED_PLANT_MANPOWER_CAPACITY);
    }


    @Test
    @Transactional
    public void getAllPowerPlantsByWorkingHourIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where workingHour equals to DEFAULT_WORKING_HOUR
        defaultPowerPlantShouldBeFound("workingHour.equals=" + DEFAULT_WORKING_HOUR);

        // Get all the powerPlantList where workingHour equals to UPDATED_WORKING_HOUR
        defaultPowerPlantShouldNotBeFound("workingHour.equals=" + UPDATED_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByWorkingHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where workingHour not equals to DEFAULT_WORKING_HOUR
        defaultPowerPlantShouldNotBeFound("workingHour.notEquals=" + DEFAULT_WORKING_HOUR);

        // Get all the powerPlantList where workingHour not equals to UPDATED_WORKING_HOUR
        defaultPowerPlantShouldBeFound("workingHour.notEquals=" + UPDATED_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByWorkingHourIsInShouldWork() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where workingHour in DEFAULT_WORKING_HOUR or UPDATED_WORKING_HOUR
        defaultPowerPlantShouldBeFound("workingHour.in=" + DEFAULT_WORKING_HOUR + "," + UPDATED_WORKING_HOUR);

        // Get all the powerPlantList where workingHour equals to UPDATED_WORKING_HOUR
        defaultPowerPlantShouldNotBeFound("workingHour.in=" + UPDATED_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByWorkingHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where workingHour is not null
        defaultPowerPlantShouldBeFound("workingHour.specified=true");

        // Get all the powerPlantList where workingHour is null
        defaultPowerPlantShouldNotBeFound("workingHour.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerPlantsByWorkingHourContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where workingHour contains DEFAULT_WORKING_HOUR
        defaultPowerPlantShouldBeFound("workingHour.contains=" + DEFAULT_WORKING_HOUR);

        // Get all the powerPlantList where workingHour contains UPDATED_WORKING_HOUR
        defaultPowerPlantShouldNotBeFound("workingHour.contains=" + UPDATED_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllPowerPlantsByWorkingHourNotContainsSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        // Get all the powerPlantList where workingHour does not contain DEFAULT_WORKING_HOUR
        defaultPowerPlantShouldNotBeFound("workingHour.doesNotContain=" + DEFAULT_WORKING_HOUR);

        // Get all the powerPlantList where workingHour does not contain UPDATED_WORKING_HOUR
        defaultPowerPlantShouldBeFound("workingHour.doesNotContain=" + UPDATED_WORKING_HOUR);
    }


    @Test
    @Transactional
    public void getAllPowerPlantsByPlantLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);
        PlantLocation plantLocation = PlantLocationResourceIT.createEntity(em);
        em.persist(plantLocation);
        em.flush();
        powerPlant.setPlantLocation(plantLocation);
        powerPlantRepository.saveAndFlush(powerPlant);
        Long plantLocationId = plantLocation.getId();

        // Get all the powerPlantList where plantLocation equals to plantLocationId
        defaultPowerPlantShouldBeFound("plantLocationId.equals=" + plantLocationId);

        // Get all the powerPlantList where plantLocation equals to plantLocationId + 1
        defaultPowerPlantShouldNotBeFound("plantLocationId.equals=" + (plantLocationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPowerPlantShouldBeFound(String filter) throws Exception {
        restPowerPlantMockMvc.perform(get("/api/power-plants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].plantOutput").value(hasItem(DEFAULT_PLANT_OUTPUT)))
            .andExpect(jsonPath("$.[*].outputUnit").value(hasItem(DEFAULT_OUTPUT_UNIT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].plantManpowerCapacity").value(hasItem(DEFAULT_PLANT_MANPOWER_CAPACITY)))
            .andExpect(jsonPath("$.[*].workingHour").value(hasItem(DEFAULT_WORKING_HOUR)));

        // Check, that the count call also returns 1
        restPowerPlantMockMvc.perform(get("/api/power-plants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPowerPlantShouldNotBeFound(String filter) throws Exception {
        restPowerPlantMockMvc.perform(get("/api/power-plants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPowerPlantMockMvc.perform(get("/api/power-plants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPowerPlant() throws Exception {
        // Get the powerPlant
        restPowerPlantMockMvc.perform(get("/api/power-plants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePowerPlant() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        int databaseSizeBeforeUpdate = powerPlantRepository.findAll().size();

        // Update the powerPlant
        PowerPlant updatedPowerPlant = powerPlantRepository.findById(powerPlant.getId()).get();
        // Disconnect from session so that the updates on updatedPowerPlant are not directly saved in db
        em.detach(updatedPowerPlant);
        updatedPowerPlant
            .name(UPDATED_NAME)
            .plantOutput(UPDATED_PLANT_OUTPUT)
            .outputUnit(UPDATED_OUTPUT_UNIT)
            .type(UPDATED_TYPE)
            .plantManpowerCapacity(UPDATED_PLANT_MANPOWER_CAPACITY)
            .workingHour(UPDATED_WORKING_HOUR);
        PowerPlantDTO powerPlantDTO = powerPlantMapper.toDto(updatedPowerPlant);

        restPowerPlantMockMvc.perform(put("/api/power-plants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerPlantDTO)))
            .andExpect(status().isOk());

        // Validate the PowerPlant in the database
        List<PowerPlant> powerPlantList = powerPlantRepository.findAll();
        assertThat(powerPlantList).hasSize(databaseSizeBeforeUpdate);
        PowerPlant testPowerPlant = powerPlantList.get(powerPlantList.size() - 1);
        assertThat(testPowerPlant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPowerPlant.getPlantOutput()).isEqualTo(UPDATED_PLANT_OUTPUT);
        assertThat(testPowerPlant.getOutputUnit()).isEqualTo(UPDATED_OUTPUT_UNIT);
        assertThat(testPowerPlant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPowerPlant.getPlantManpowerCapacity()).isEqualTo(UPDATED_PLANT_MANPOWER_CAPACITY);
        assertThat(testPowerPlant.getWorkingHour()).isEqualTo(UPDATED_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingPowerPlant() throws Exception {
        int databaseSizeBeforeUpdate = powerPlantRepository.findAll().size();

        // Create the PowerPlant
        PowerPlantDTO powerPlantDTO = powerPlantMapper.toDto(powerPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerPlantMockMvc.perform(put("/api/power-plants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerPlantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PowerPlant in the database
        List<PowerPlant> powerPlantList = powerPlantRepository.findAll();
        assertThat(powerPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePowerPlant() throws Exception {
        // Initialize the database
        powerPlantRepository.saveAndFlush(powerPlant);

        int databaseSizeBeforeDelete = powerPlantRepository.findAll().size();

        // Delete the powerPlant
        restPowerPlantMockMvc.perform(delete("/api/power-plants/{id}", powerPlant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PowerPlant> powerPlantList = powerPlantRepository.findAll();
        assertThat(powerPlantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

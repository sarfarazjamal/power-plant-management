package com.jamal.power.plant.web.rest;

import com.jamal.power.plant.service.PowerPlantService;
import com.jamal.power.plant.service.dto.PercentageDTO;
import com.jamal.power.plant.web.rest.errors.BadRequestAlertException;
import com.jamal.power.plant.service.dto.PowerPlantDTO;
import com.jamal.power.plant.service.dto.PowerPlantCriteria;
import com.jamal.power.plant.service.PowerPlantQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jamal.power.plant.domain.PowerPlant}.
 */
@RestController
@RequestMapping("/api")
public class PowerPlantResource {

    private final Logger log = LoggerFactory.getLogger(PowerPlantResource.class);

    private static final String ENTITY_NAME = "powerPlant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PowerPlantService powerPlantService;

    private final PowerPlantQueryService powerPlantQueryService;

    public PowerPlantResource(PowerPlantService powerPlantService, PowerPlantQueryService powerPlantQueryService) {
        this.powerPlantService = powerPlantService;
        this.powerPlantQueryService = powerPlantQueryService;
    }

    /**
     * {@code POST  /power-plants} : Create a new powerPlant.
     *
     * @param powerPlantDTO the powerPlantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new powerPlantDTO, or with status {@code 400 (Bad Request)} if the powerPlant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/power-plants")
    public ResponseEntity<PowerPlantDTO> createPowerPlant(@RequestBody PowerPlantDTO powerPlantDTO) throws URISyntaxException {
        log.debug("REST request to save PowerPlant : {}", powerPlantDTO);
        if (powerPlantDTO.getId() != null) {
            throw new BadRequestAlertException("A new powerPlant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PowerPlantDTO result = powerPlantService.save(powerPlantDTO);
        return ResponseEntity.created(new URI("/api/power-plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /power-plants} : Updates an existing powerPlant.
     *
     * @param powerPlantDTO the powerPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerPlantDTO,
     * or with status {@code 400 (Bad Request)} if the powerPlantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the powerPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/power-plants")
    public ResponseEntity<PowerPlantDTO> updatePowerPlant(@RequestBody PowerPlantDTO powerPlantDTO) throws URISyntaxException {
        log.debug("REST request to update PowerPlant : {}", powerPlantDTO);
        if (powerPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PowerPlantDTO result = powerPlantService.save(powerPlantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, powerPlantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /power-plants} : get all the powerPlants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of powerPlants in body.
     */
    @GetMapping("/power-plants")
    public ResponseEntity<List<PowerPlantDTO>> getAllPowerPlants(PowerPlantCriteria criteria, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "DESC") String order, @RequestParam(defaultValue = "name") String columnName) {
        log.debug("REST request to get PowerPlants by criteria: {}", criteria);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, columnName));
        Page<PowerPlantDTO> page = powerPlantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /power-plants/count} : count all the powerPlants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/power-plants/count-percentage")
    public ResponseEntity<PercentageDTO> countPowerPlants(PowerPlantCriteria criteria, Pageable pageable) {
        log.debug("REST request to count PowerPlants by criteria: {}", criteria);
        log.debug("REST request to get PowerPlants by criteria: {}", criteria);

        BigDecimal actualCountByLocation = powerPlantQueryService.findByLocationCriteria(criteria, pageable);

        BigDecimal totalCount = powerPlantService.findPlantCapacityCount();

        BigDecimal ONE_HUNDRED = new BigDecimal(100);
        BigDecimal percentageValue=actualCountByLocation.multiply(ONE_HUNDRED).divide(totalCount,RoundingMode.HALF_UP);
        PercentageDTO parDTO=new PercentageDTO();
        parDTO.setActualValue(""+actualCountByLocation);
        parDTO.setPlantOutputPercentage(""+percentageValue);
        return ResponseEntity.ok().body(parDTO);
    }

    /**
     * {@code GET  /power-plants/:id} : get the "id" powerPlant.
     *
     * @param id the id of the powerPlantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the powerPlantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/power-plants/{id}")
    public ResponseEntity<PowerPlantDTO> getPowerPlant(@PathVariable Long id) {
        log.debug("REST request to get PowerPlant : {}", id);
        Optional<PowerPlantDTO> powerPlantDTO = powerPlantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(powerPlantDTO);
    }

    /**
     * {@code DELETE  /power-plants/:id} : delete the "id" powerPlant.
     *
     * @param id the id of the powerPlantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/power-plants/{id}")
    public ResponseEntity<Void> deletePowerPlant(@PathVariable Long id) {
        log.debug("REST request to delete PowerPlant : {}", id);
        powerPlantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

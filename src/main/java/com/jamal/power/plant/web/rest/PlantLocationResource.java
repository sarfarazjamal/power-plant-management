package com.jamal.power.plant.web.rest;

import com.jamal.power.plant.service.PlantLocationService;
import com.jamal.power.plant.web.rest.errors.BadRequestAlertException;
import com.jamal.power.plant.service.dto.PlantLocationDTO;
import com.jamal.power.plant.service.dto.PlantLocationCriteria;
import com.jamal.power.plant.service.PlantLocationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jamal.power.plant.domain.PlantLocation}.
 */
@RestController
@RequestMapping("/api")
public class PlantLocationResource {

    private final Logger log = LoggerFactory.getLogger(PlantLocationResource.class);

    private static final String ENTITY_NAME = "plantLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantLocationService plantLocationService;

    private final PlantLocationQueryService plantLocationQueryService;

    public PlantLocationResource(PlantLocationService plantLocationService, PlantLocationQueryService plantLocationQueryService) {
        this.plantLocationService = plantLocationService;
        this.plantLocationQueryService = plantLocationQueryService;
    }

    /**
     * {@code POST  /plant-locations} : Create a new plantLocation.
     *
     * @param plantLocationDTO the plantLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantLocationDTO, or with status {@code 400 (Bad Request)} if the plantLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plant-locations")
    public ResponseEntity<PlantLocationDTO> createPlantLocation(@RequestBody PlantLocationDTO plantLocationDTO) throws URISyntaxException {
        log.debug("REST request to save PlantLocation : {}", plantLocationDTO);
        if (plantLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new plantLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantLocationDTO result = plantLocationService.save(plantLocationDTO);
        return ResponseEntity.created(new URI("/api/plant-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plant-locations} : Updates an existing plantLocation.
     *
     * @param plantLocationDTO the plantLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantLocationDTO,
     * or with status {@code 400 (Bad Request)} if the plantLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plant-locations")
    public ResponseEntity<PlantLocationDTO> updatePlantLocation(@RequestBody PlantLocationDTO plantLocationDTO) throws URISyntaxException {
        log.debug("REST request to update PlantLocation : {}", plantLocationDTO);
        if (plantLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlantLocationDTO result = plantLocationService.save(plantLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plantLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plant-locations} : get all the plantLocations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantLocations in body.
     */
    @GetMapping("/plant-locations")
    public ResponseEntity<List<PlantLocationDTO>> getAllPlantLocations(PlantLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlantLocations by criteria: {}", criteria);
        Page<PlantLocationDTO> page = plantLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plant-locations/count} : count all the plantLocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plant-locations/count")
    public ResponseEntity<Long> countPlantLocations(PlantLocationCriteria criteria) {
        log.debug("REST request to count PlantLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(plantLocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plant-locations/:id} : get the "id" plantLocation.
     *
     * @param id the id of the plantLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plant-locations/{id}")
    public ResponseEntity<PlantLocationDTO> getPlantLocation(@PathVariable Long id) {
        log.debug("REST request to get PlantLocation : {}", id);
        Optional<PlantLocationDTO> plantLocationDTO = plantLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plantLocationDTO);
    }

    /**
     * {@code DELETE  /plant-locations/:id} : delete the "id" plantLocation.
     *
     * @param id the id of the plantLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plant-locations/{id}")
    public ResponseEntity<Void> deletePlantLocation(@PathVariable Long id) {
        log.debug("REST request to delete PlantLocation : {}", id);
        plantLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

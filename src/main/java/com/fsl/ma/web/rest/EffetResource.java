package com.fsl.ma.web.rest;
import com.fsl.ma.domain.Effet;
import com.fsl.ma.service.EffetService;
import com.fsl.ma.web.rest.errors.BadRequestAlertException;
import com.fsl.ma.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Effet.
 */
@RestController
@RequestMapping("/api")
public class EffetResource {

    private final Logger log = LoggerFactory.getLogger(EffetResource.class);

    private static final String ENTITY_NAME = "effet";

    private final EffetService effetService;

    public EffetResource(EffetService effetService) {
        this.effetService = effetService;
    }

    /**
     * POST  /effets : Create a new effet.
     *
     * @param effet the effet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new effet, or with status 400 (Bad Request) if the effet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/effets")
    public ResponseEntity<Effet> createEffet(@RequestBody Effet effet) throws URISyntaxException {
        log.debug("REST request to save Effet : {}", effet);
        if (effet.getId() != null) {
            throw new BadRequestAlertException("A new effet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Effet result = effetService.save(effet);
        return ResponseEntity.created(new URI("/api/effets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /effets : Updates an existing effet.
     *
     * @param effet the effet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated effet,
     * or with status 400 (Bad Request) if the effet is not valid,
     * or with status 500 (Internal Server Error) if the effet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/effets")
    public ResponseEntity<Effet> updateEffet(@RequestBody Effet effet) throws URISyntaxException {
        log.debug("REST request to update Effet : {}", effet);
        if (effet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Effet result = effetService.save(effet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, effet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /effets : get all the effets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of effets in body
     */
    @GetMapping("/effets")
    public List<Effet> getAllEffets() {
        log.debug("REST request to get all Effets");
        return effetService.findAll();
    }

    /**
     * GET  /effets/:id : get the "id" effet.
     *
     * @param id the id of the effet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the effet, or with status 404 (Not Found)
     */
    @GetMapping("/effets/{id}")
    public ResponseEntity<Effet> getEffet(@PathVariable Long id) {
        log.debug("REST request to get Effet : {}", id);
        Optional<Effet> effet = effetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(effet);
    }

    /**
     * DELETE  /effets/:id : delete the "id" effet.
     *
     * @param id the id of the effet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/effets/{id}")
    public ResponseEntity<Void> deleteEffet(@PathVariable Long id) {
        log.debug("REST request to delete Effet : {}", id);
        effetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

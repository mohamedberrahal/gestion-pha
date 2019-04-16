package com.fsl.ma.web.rest;
import com.fsl.ma.domain.Maladie;
import com.fsl.ma.service.MaladieService;
import com.fsl.ma.web.rest.errors.BadRequestAlertException;
import com.fsl.ma.web.rest.util.HeaderUtil;
import com.fsl.ma.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Maladie.
 */
@RestController
@RequestMapping("/api")
public class MaladieResource {

    private final Logger log = LoggerFactory.getLogger(MaladieResource.class);

    private static final String ENTITY_NAME = "maladie";

    private final MaladieService maladieService;

    public MaladieResource(MaladieService maladieService) {
        this.maladieService = maladieService;
    }

    /**
     * POST  /maladies : Create a new maladie.
     *
     * @param maladie the maladie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new maladie, or with status 400 (Bad Request) if the maladie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/maladies")
    public ResponseEntity<Maladie> createMaladie(@Valid @RequestBody Maladie maladie) throws URISyntaxException {
        log.debug("REST request to save Maladie : {}", maladie);
        if (maladie.getId() != null) {
            throw new BadRequestAlertException("A new maladie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Maladie result = maladieService.save(maladie);
        return ResponseEntity.created(new URI("/api/maladies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maladies : Updates an existing maladie.
     *
     * @param maladie the maladie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated maladie,
     * or with status 400 (Bad Request) if the maladie is not valid,
     * or with status 500 (Internal Server Error) if the maladie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/maladies")
    public ResponseEntity<Maladie> updateMaladie(@Valid @RequestBody Maladie maladie) throws URISyntaxException {
        log.debug("REST request to update Maladie : {}", maladie);
        if (maladie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Maladie result = maladieService.save(maladie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, maladie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maladies : get all the maladies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of maladies in body
     */
    @GetMapping("/maladies")
    public ResponseEntity<List<Maladie>> getAllMaladies(Pageable pageable) {
        log.debug("REST request to get a page of Maladies");
        Page<Maladie> page = maladieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/maladies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /maladies/:id : get the "id" maladie.
     *
     * @param id the id of the maladie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the maladie, or with status 404 (Not Found)
     */
    @GetMapping("/maladies/{id}")
    public ResponseEntity<Maladie> getMaladie(@PathVariable Long id) {
        log.debug("REST request to get Maladie : {}", id);
        Optional<Maladie> maladie = maladieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(maladie);
    }

    /**
     * DELETE  /maladies/:id : delete the "id" maladie.
     *
     * @param id the id of the maladie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/maladies/{id}")
    public ResponseEntity<Void> deleteMaladie(@PathVariable Long id) {
        log.debug("REST request to delete Maladie : {}", id);
        maladieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

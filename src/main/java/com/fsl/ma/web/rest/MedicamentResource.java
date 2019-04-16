package com.fsl.ma.web.rest;
import com.fsl.ma.domain.Medicament;
import com.fsl.ma.repository.MedicamentRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Medicament.
 */
@RestController
@RequestMapping("/api")
public class MedicamentResource {

    private final Logger log = LoggerFactory.getLogger(MedicamentResource.class);

    private static final String ENTITY_NAME = "medicament";

    private final MedicamentRepository medicamentRepository;

    public MedicamentResource(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    /**
     * POST  /medicaments : Create a new medicament.
     *
     * @param medicament the medicament to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicament, or with status 400 (Bad Request) if the medicament has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicaments")
    public ResponseEntity<Medicament> createMedicament(@RequestBody Medicament medicament) throws URISyntaxException {
        log.debug("REST request to save Medicament : {}", medicament);
        if (medicament.getId() != null) {
            throw new BadRequestAlertException("A new medicament cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medicament result = medicamentRepository.save(medicament);
        return ResponseEntity.created(new URI("/api/medicaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicaments : Updates an existing medicament.
     *
     * @param medicament the medicament to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicament,
     * or with status 400 (Bad Request) if the medicament is not valid,
     * or with status 500 (Internal Server Error) if the medicament couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicaments")
    public ResponseEntity<Medicament> updateMedicament(@RequestBody Medicament medicament) throws URISyntaxException {
        log.debug("REST request to update Medicament : {}", medicament);
        if (medicament.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Medicament result = medicamentRepository.save(medicament);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicament.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicaments : get all the medicaments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicaments in body
     */
    @GetMapping("/medicaments")
    public ResponseEntity<List<Medicament>> getAllMedicaments(Pageable pageable) {
        log.debug("REST request to get a page of Medicaments");
        Page<Medicament> page = medicamentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medicaments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medicaments/:id : get the "id" medicament.
     *
     * @param id the id of the medicament to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicament, or with status 404 (Not Found)
     */
    @GetMapping("/medicaments/{id}")
    public ResponseEntity<Medicament> getMedicament(@PathVariable Long id) {
        log.debug("REST request to get Medicament : {}", id);
        Optional<Medicament> medicament = medicamentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medicament);
    }

    /**
     * DELETE  /medicaments/:id : delete the "id" medicament.
     *
     * @param id the id of the medicament to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicaments/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        log.debug("REST request to delete Medicament : {}", id);
        medicamentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

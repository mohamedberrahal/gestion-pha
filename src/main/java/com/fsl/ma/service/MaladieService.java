package com.fsl.ma.service;

import com.fsl.ma.domain.Maladie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Maladie.
 */
public interface MaladieService {

    /**
     * Save a maladie.
     *
     * @param maladie the entity to save
     * @return the persisted entity
     */
    Maladie save(Maladie maladie);

    /**
     * Get all the maladies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Maladie> findAll(Pageable pageable);


    /**
     * Get the "id" maladie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Maladie> findOne(Long id);

    /**
     * Delete the "id" maladie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

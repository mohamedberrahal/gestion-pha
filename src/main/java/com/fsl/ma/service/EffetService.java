package com.fsl.ma.service;

import com.fsl.ma.domain.Effet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Effet.
 */
public interface EffetService {

    /**
     * Save a effet.
     *
     * @param effet the entity to save
     * @return the persisted entity
     */
    Effet save(Effet effet);

    /**
     * Get all the effets.
     *
     * @return the list of entities
     */
    List<Effet> findAll();


    /**
     * Get the "id" effet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Effet> findOne(Long id);

    /**
     * Delete the "id" effet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

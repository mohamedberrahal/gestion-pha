package com.fsl.ma.service;

import com.fsl.ma.domain.Destination;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Destination.
 */
public interface DestinationService {

    /**
     * Save a destination.
     *
     * @param destination the entity to save
     * @return the persisted entity
     */
    Destination save(Destination destination);

    /**
     * Get all the destinations.
     *
     * @return the list of entities
     */
    List<Destination> findAll();


    /**
     * Get the "id" destination.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Destination> findOne(Long id);

    /**
     * Delete the "id" destination.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

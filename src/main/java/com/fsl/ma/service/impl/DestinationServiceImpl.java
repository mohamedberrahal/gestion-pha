package com.fsl.ma.service.impl;

import com.fsl.ma.service.DestinationService;
import com.fsl.ma.domain.Destination;
import com.fsl.ma.repository.DestinationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Destination.
 */
@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final Logger log = LoggerFactory.getLogger(DestinationServiceImpl.class);

    private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    /**
     * Save a destination.
     *
     * @param destination the entity to save
     * @return the persisted entity
     */
    @Override
    public Destination save(Destination destination) {
        log.debug("Request to save Destination : {}", destination);
        return destinationRepository.save(destination);
    }

    /**
     * Get all the destinations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Destination> findAll() {
        log.debug("Request to get all Destinations");
        return destinationRepository.findAll();
    }


    /**
     * Get one destination by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Destination> findOne(Long id) {
        log.debug("Request to get Destination : {}", id);
        return destinationRepository.findById(id);
    }

    /**
     * Delete the destination by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Destination : {}", id);
        destinationRepository.deleteById(id);
    }
}

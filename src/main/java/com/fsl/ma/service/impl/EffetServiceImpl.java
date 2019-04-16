package com.fsl.ma.service.impl;

import com.fsl.ma.service.EffetService;
import com.fsl.ma.domain.Effet;
import com.fsl.ma.repository.EffetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Effet.
 */
@Service
@Transactional
public class EffetServiceImpl implements EffetService {

    private final Logger log = LoggerFactory.getLogger(EffetServiceImpl.class);

    private final EffetRepository effetRepository;

    public EffetServiceImpl(EffetRepository effetRepository) {
        this.effetRepository = effetRepository;
    }

    /**
     * Save a effet.
     *
     * @param effet the entity to save
     * @return the persisted entity
     */
    @Override
    public Effet save(Effet effet) {
        log.debug("Request to save Effet : {}", effet);
        return effetRepository.save(effet);
    }

    /**
     * Get all the effets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Effet> findAll() {
        log.debug("Request to get all Effets");
        return effetRepository.findAll();
    }


    /**
     * Get one effet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Effet> findOne(Long id) {
        log.debug("Request to get Effet : {}", id);
        return effetRepository.findById(id);
    }

    /**
     * Delete the effet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Effet : {}", id);
        effetRepository.deleteById(id);
    }
}

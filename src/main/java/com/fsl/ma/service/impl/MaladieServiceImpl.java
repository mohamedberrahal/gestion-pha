package com.fsl.ma.service.impl;

import com.fsl.ma.service.MaladieService;
import com.fsl.ma.domain.Maladie;
import com.fsl.ma.repository.MaladieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Maladie.
 */
@Service
@Transactional
public class MaladieServiceImpl implements MaladieService {

    private final Logger log = LoggerFactory.getLogger(MaladieServiceImpl.class);

    private final MaladieRepository maladieRepository;

    public MaladieServiceImpl(MaladieRepository maladieRepository) {
        this.maladieRepository = maladieRepository;
    }

    /**
     * Save a maladie.
     *
     * @param maladie the entity to save
     * @return the persisted entity
     */
    @Override
    public Maladie save(Maladie maladie) {
        log.debug("Request to save Maladie : {}", maladie);
        return maladieRepository.save(maladie);
    }

    /**
     * Get all the maladies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Maladie> findAll(Pageable pageable) {
        log.debug("Request to get all Maladies");
        return maladieRepository.findAll(pageable);
    }


    /**
     * Get one maladie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Maladie> findOne(Long id) {
        log.debug("Request to get Maladie : {}", id);
        return maladieRepository.findById(id);
    }

    /**
     * Delete the maladie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Maladie : {}", id);
        maladieRepository.deleteById(id);
    }
}

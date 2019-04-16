package com.fsl.ma.repository;

import com.fsl.ma.domain.Effet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Effet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EffetRepository extends JpaRepository<Effet, Long> {

}

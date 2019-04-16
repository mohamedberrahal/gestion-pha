package com.fsl.ma.repository;

import com.fsl.ma.domain.Destination;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Destination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

}

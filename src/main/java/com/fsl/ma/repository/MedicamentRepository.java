package com.fsl.ma.repository;

import com.fsl.ma.domain.Medicament;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Medicament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

}

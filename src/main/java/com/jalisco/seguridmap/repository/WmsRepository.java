package com.jalisco.seguridmap.repository;

import com.jalisco.seguridmap.domain.Wms;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Wms entity.
 */
@SuppressWarnings("unused")
public interface WmsRepository extends JpaRepository<Wms,Long> {

}

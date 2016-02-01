package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AudioGeoTagHeritageEntity entity.
 */
public interface AudioGeoTagHeritageEntityRepository extends JpaRepository<AudioGeoTagHeritageEntity,Long> {

	public List<AudioGeoTagHeritageEntity> findAllByOrderByIdAsc();
}

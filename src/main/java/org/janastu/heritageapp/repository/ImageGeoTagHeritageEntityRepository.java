package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ImageGeoTagHeritageEntity entity.
 */
public interface ImageGeoTagHeritageEntityRepository extends JpaRepository<ImageGeoTagHeritageEntity,Long> {
	
	 public List<ImageGeoTagHeritageEntity> findAllByOrderByIdAsc();

}

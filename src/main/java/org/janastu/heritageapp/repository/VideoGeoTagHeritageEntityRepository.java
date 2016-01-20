package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VideoGeoTagHeritageEntity entity.
 */
public interface VideoGeoTagHeritageEntityRepository extends JpaRepository<VideoGeoTagHeritageEntity,Long> {

}

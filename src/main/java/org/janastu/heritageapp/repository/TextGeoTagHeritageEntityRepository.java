package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TextGeoTagHeritageEntity entity.
 */
public interface TextGeoTagHeritageEntityRepository extends JpaRepository<TextGeoTagHeritageEntity,Long> {

}

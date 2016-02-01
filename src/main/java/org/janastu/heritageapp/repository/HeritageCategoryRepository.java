package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageCategory entity.
 */
public interface HeritageCategoryRepository extends JpaRepository<HeritageCategory,Long> {
	
	public HeritageCategory findByCategoryName(String heritageCategory);

}

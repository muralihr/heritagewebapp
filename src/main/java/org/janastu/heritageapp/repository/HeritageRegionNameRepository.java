package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageRegionName;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageRegionName entity.
 */
public interface HeritageRegionNameRepository extends JpaRepository<HeritageRegionName,Long> {

}

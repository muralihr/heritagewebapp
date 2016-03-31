package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageGroup entity.
 */
public interface HeritageGroupRepository extends JpaRepository<HeritageGroup,Long> {

}

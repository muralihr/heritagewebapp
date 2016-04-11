package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.domain.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HeritageGroup entity.
 */
public interface HeritageGroupRepository extends JpaRepository<HeritageGroup,Long> {
	
    Optional<HeritageGroup> findOneByName(String name);

    Optional<HeritageGroup> findOneById(Long userId);

}

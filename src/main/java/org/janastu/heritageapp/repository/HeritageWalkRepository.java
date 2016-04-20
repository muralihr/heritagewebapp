package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageWalk;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageWalk entity.
 */
public interface HeritageWalkRepository extends JpaRepository<HeritageWalk,Long> {

    @Query("select heritageWalk from HeritageWalk heritageWalk where heritageWalk.user.login = ?#{principal.username}")
    List<HeritageWalk> findByUserIsCurrentUser();

}

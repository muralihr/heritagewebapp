package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageMedia;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageMedia entity.
 */
public interface HeritageMediaRepository extends JpaRepository<HeritageMedia,Long> {

    @Query("select heritageMedia from HeritageMedia heritageMedia where heritageMedia.user.login = ?#{principal.username}")
    List<HeritageMedia> findByUserIsCurrentUser();

}

package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageSlide;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageSlide entity.
 */
public interface HeritageSlideRepository extends JpaRepository<HeritageSlide,Long> {

    @Query("select heritageSlide from HeritageSlide heritageSlide where heritageSlide.user.login = ?#{principal.username}")
    List<HeritageSlide> findByUserIsCurrentUser();

}

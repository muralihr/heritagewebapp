package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageApp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageApp entity.
 */
public interface HeritageAppRepository extends JpaRepository<HeritageApp,Long> {

    @Query("select distinct heritageApp from HeritageApp heritageApp left join fetch heritageApp.regions left join fetch heritageApp.groups left join fetch heritageApp.languages left join fetch heritageApp.categorys")
    List<HeritageApp> findAllWithEagerRelationships();

    @Query("select heritageApp from HeritageApp heritageApp left join fetch heritageApp.regions left join fetch heritageApp.groups left join fetch heritageApp.languages left join fetch heritageApp.categorys where heritageApp.id =:id")
    HeritageApp findOneWithEagerRelationships(@Param("id") Long id);

	HeritageApp findByName(String appName);

}

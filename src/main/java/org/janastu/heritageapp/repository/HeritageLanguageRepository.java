package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageLanguage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageLanguage entity.
 */
public interface HeritageLanguageRepository extends JpaRepository<HeritageLanguage,Long> {

	public HeritageLanguage findByHeritageLanguage(String heritageLanguage); //heritageLanguage
}

package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageSlide;
import org.janastu.heritageapp.web.rest.dto.HeritageSlideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageSlide.
 */
public interface HeritageSlideService {

    /**
     * Save a heritageSlide.
     * @return the persisted entity
     */
    public HeritageSlideDTO save(HeritageSlideDTO heritageSlideDTO);

    /**
     *  get all the heritageSlides.
     *  @return the list of entities
     */
    public Page<HeritageSlide> findAll(Pageable pageable);

    /**
     *  get the "id" heritageSlide.
     *  @return the entity
     */
    public HeritageSlideDTO findOne(Long id);

    /**
     *  delete the "id" heritageSlide.
     */
    public void delete(Long id);
}

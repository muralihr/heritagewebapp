package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageRegionName;
import org.janastu.heritageapp.web.rest.dto.HeritageRegionNameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageRegionName.
 */
public interface HeritageRegionNameService {

    /**
     * Save a heritageRegionName.
     * @return the persisted entity
     */
    public HeritageRegionNameDTO save(HeritageRegionNameDTO heritageRegionNameDTO);

    /**
     *  get all the heritageRegionNames.
     *  @return the list of entities
     */
    public Page<HeritageRegionName> findAll(Pageable pageable);

    /**
     *  get the "id" heritageRegionName.
     *  @return the entity
     */
    public HeritageRegionNameDTO findOne(Long id);

    /**
     *  delete the "id" heritageRegionName.
     */
    public void delete(Long id);
}

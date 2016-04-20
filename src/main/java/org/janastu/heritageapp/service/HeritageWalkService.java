package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageWalk;
import org.janastu.heritageapp.web.rest.dto.HeritageWalkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageWalk.
 */
public interface HeritageWalkService {

    /**
     * Save a heritageWalk.
     * @return the persisted entity
     */
    public HeritageWalkDTO save(HeritageWalkDTO heritageWalkDTO);

    /**
     *  get all the heritageWalks.
     *  @return the list of entities
     */
    public Page<HeritageWalk> findAll(Pageable pageable);

    /**
     *  get the "id" heritageWalk.
     *  @return the entity
     */
    public HeritageWalkDTO findOne(Long id);

    /**
     *  delete the "id" heritageWalk.
     */
    public void delete(Long id);
}

package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageMedia;
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageMedia.
 */
public interface HeritageMediaService {

    /**
     * Save a heritageMedia.
     * @return the persisted entity
     */
    public HeritageMediaDTO save(HeritageMediaDTO heritageMediaDTO);

    /**
     *  get all the heritageMedias.
     *  @return the list of entities
     */
    public Page<HeritageMedia> findAll(Pageable pageable);

    /**
     *  get the "id" heritageMedia.
     *  @return the entity
     */
    public HeritageMediaDTO findOne(Long id);

    /**
     *  delete the "id" heritageMedia.
     */
    public void delete(Long id);

	public List<HeritageMedia> findAllAsAList();
}

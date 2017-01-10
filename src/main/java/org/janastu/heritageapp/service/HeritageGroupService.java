package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageGroup.
 */
public interface HeritageGroupService {

    /**
     * Save a heritageGroup.
     * @return the persisted entity
     */
    public HeritageGroupDTO save(HeritageGroupDTO heritageGroupDTO);

    /**
     *  get all the heritageGroups.
     *  @return the list of entities
     */
    public Page<HeritageGroup> findAll(Pageable pageable);

    /**
     *  get the "id" heritageGroup.
     *  @return the entity
     */
    public HeritageGroupDTO findOne(Long id);

    /**
     *  delete the "id" heritageGroup.
     */
    public void delete(Long id);

	public List<HeritageGroup> findAll();
}

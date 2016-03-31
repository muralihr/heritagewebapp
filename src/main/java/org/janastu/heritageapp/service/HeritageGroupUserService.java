package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageGroupUser;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageGroupUser.
 */
public interface HeritageGroupUserService {

    /**
     * Save a heritageGroupUser.
     * @return the persisted entity
     */
    public HeritageGroupUserDTO save(HeritageGroupUserDTO heritageGroupUserDTO);

    /**
     *  get all the heritageGroupUsers.
     *  @return the list of entities
     */
    public Page<HeritageGroupUser> findAll(Pageable pageable);

    /**
     *  get the "id" heritageGroupUser.
     *  @return the entity
     */
    public HeritageGroupUserDTO findOne(Long id);

    /**
     *  delete the "id" heritageGroupUser.
     */
    public void delete(Long id);
}

package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageApp;
import org.janastu.heritageapp.web.rest.dto.HeritageAppDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageApp.
 */
public interface HeritageAppService {

    /**
     * Save a heritageApp.
     * @return the persisted entity
     */
    public HeritageAppDTO save(HeritageAppDTO heritageAppDTO);

    /**
     *  get all the heritageApps.
     *  @return the list of entities
     */
    public Page<HeritageApp> findAll(Pageable pageable);

    /**
     *  get the "id" heritageApp.
     *  @return the entity
     */
    public HeritageAppDTO findOne(Long id);

    /**
     *  delete the "id" heritageApp.
     */
    public void delete(Long id);

	public HeritageAppDTO  findByName(String appId);
}

package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing AudioGeoTagHeritageEntity.
 */
public interface AudioGeoTagHeritageEntityService {

    /**
     * Save a audioGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public AudioGeoTagHeritageEntityDTO save(AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO);

    /**
     *  get all the audioGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    public Page<AudioGeoTagHeritageEntity> findAll(Pageable pageable);

    /**
     *  get the "id" audioGeoTagHeritageEntity.
     *  @return the entity
     */
    public AudioGeoTagHeritageEntityDTO findOne(Long id);

    /**
     *  delete the "id" audioGeoTagHeritageEntity.
     */
    public void delete(Long id);
}

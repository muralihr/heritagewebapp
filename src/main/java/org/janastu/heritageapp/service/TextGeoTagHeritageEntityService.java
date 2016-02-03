package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TextGeoTagHeritageEntity.
 */
public interface TextGeoTagHeritageEntityService {

    /**
     * Save a textGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public TextGeoTagHeritageEntityDTO save(TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO);

    /**
     *  get all the textGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    public Page<TextGeoTagHeritageEntity> findAll(Pageable pageable);
    public List<TextGeoTagHeritageEntity>  findAllAsAList();
    /**
     *  get the "id" textGeoTagHeritageEntity.
     *  @return the entity
     */
    public TextGeoTagHeritageEntityDTO findOne(Long id);

    /**
     *  delete the "id" textGeoTagHeritageEntity.
     */
    public void delete(Long id);
}

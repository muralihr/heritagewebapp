package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ImageGeoTagHeritageEntity.
 */
public interface ImageGeoTagHeritageEntityService {

    /**
     * Save a imageGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public ImageGeoTagHeritageEntityDTO save(ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO);

    /**
     *  get all the imageGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    public Page<ImageGeoTagHeritageEntity> findAll(Pageable pageable);

    /**
     *  get the "id" imageGeoTagHeritageEntity.
     *  @return the entity
     */
    public ImageGeoTagHeritageEntityDTO findOne(Long id);

    /**
     *  delete the "id" imageGeoTagHeritageEntity.
     */
    public void delete(Long id);
}

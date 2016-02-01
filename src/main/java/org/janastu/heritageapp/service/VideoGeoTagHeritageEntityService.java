package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing VideoGeoTagHeritageEntity.
 */
public interface VideoGeoTagHeritageEntityService {

    /**
     * Save a videoGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public VideoGeoTagHeritageEntityDTO save(VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO);

    /**
     *  get all the videoGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    public Page<VideoGeoTagHeritageEntity> findAll(Pageable pageable);
    public List<VideoGeoTagHeritageEntity>  findAllAsAList();
    /**
     *  get the "id" videoGeoTagHeritageEntity.
     *  @return the entity
     */
    public VideoGeoTagHeritageEntityDTO findOne(Long id);

    /**
     *  delete the "id" videoGeoTagHeritageEntity.
     */
    public void delete(Long id);
}

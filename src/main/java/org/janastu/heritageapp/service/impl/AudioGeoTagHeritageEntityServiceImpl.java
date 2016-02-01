package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.AudioGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.AudioGeoTagHeritageEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AudioGeoTagHeritageEntity.
 */
@Service
@Transactional
public class AudioGeoTagHeritageEntityServiceImpl implements AudioGeoTagHeritageEntityService{

    private final Logger log = LoggerFactory.getLogger(AudioGeoTagHeritageEntityServiceImpl.class);
    
    @Inject
    private AudioGeoTagHeritageEntityRepository audioGeoTagHeritageEntityRepository;
    
    @Inject
    private AudioGeoTagHeritageEntityMapper audioGeoTagHeritageEntityMapper;
    
    /**
     * Save a audioGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public AudioGeoTagHeritageEntityDTO save(AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO) {
        log.debug("Request to save AudioGeoTagHeritageEntity : {}", audioGeoTagHeritageEntityDTO);
        AudioGeoTagHeritageEntity audioGeoTagHeritageEntity = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityDTOToAudioGeoTagHeritageEntity(audioGeoTagHeritageEntityDTO);
        audioGeoTagHeritageEntity = audioGeoTagHeritageEntityRepository.save(audioGeoTagHeritageEntity);
        AudioGeoTagHeritageEntityDTO result = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);
        return result;
    }

    /**
     *  get all the audioGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AudioGeoTagHeritageEntity> findAll(Pageable pageable) {
        log.debug("Request to get all AudioGeoTagHeritageEntitys");
        Page<AudioGeoTagHeritageEntity> result = audioGeoTagHeritageEntityRepository.findAll(pageable); 
        return result;
    }
    
    
    public List<AudioGeoTagHeritageEntity> findAllAsAList()
    {    	
    	//
    	return audioGeoTagHeritageEntityRepository.findAllByOrderByIdAsc();
    }

    /**
     *  get one audioGeoTagHeritageEntity by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AudioGeoTagHeritageEntityDTO findOne(Long id) {
        log.debug("Request to get AudioGeoTagHeritageEntity : {}", id);
        AudioGeoTagHeritageEntity audioGeoTagHeritageEntity = audioGeoTagHeritageEntityRepository.findOne(id);
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);
        return audioGeoTagHeritageEntityDTO;
    }

    /**
     *  delete the  audioGeoTagHeritageEntity by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete AudioGeoTagHeritageEntity : {}", id);
        audioGeoTagHeritageEntityRepository.delete(id);
    }
}

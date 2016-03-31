package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageMediaService;
import org.janastu.heritageapp.domain.HeritageMedia;
import org.janastu.heritageapp.repository.HeritageMediaRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageMediaMapper;
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
 * Service Implementation for managing HeritageMedia.
 */
@Service
@Transactional
public class HeritageMediaServiceImpl implements HeritageMediaService{

    private final Logger log = LoggerFactory.getLogger(HeritageMediaServiceImpl.class);
    
    @Inject
    private HeritageMediaRepository heritageMediaRepository;
    
    @Inject
    private HeritageMediaMapper heritageMediaMapper;
    
    /**
     * Save a heritageMedia.
     * @return the persisted entity
     */
    public HeritageMediaDTO save(HeritageMediaDTO heritageMediaDTO) {
        log.debug("Request to save HeritageMedia : {}", heritageMediaDTO);
        HeritageMedia heritageMedia = heritageMediaMapper.heritageMediaDTOToHeritageMedia(heritageMediaDTO);
        heritageMedia = heritageMediaRepository.save(heritageMedia);
        HeritageMediaDTO result = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);
        return result;
    }

    /**
     *  get all the heritageMedias.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageMedia> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageMedias");
        Page<HeritageMedia> result = heritageMediaRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageMedia by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageMediaDTO findOne(Long id) {
        log.debug("Request to get HeritageMedia : {}", id);
        HeritageMedia heritageMedia = heritageMediaRepository.findOne(id);
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);
        return heritageMediaDTO;
    }

    /**
     *  delete the  heritageMedia by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageMedia : {}", id);
        heritageMediaRepository.delete(id);
    }
}

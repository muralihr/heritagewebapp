package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageWalkService;
import org.janastu.heritageapp.domain.HeritageWalk;
import org.janastu.heritageapp.repository.HeritageWalkRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageWalkDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageWalkMapper;
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
 * Service Implementation for managing HeritageWalk.
 */
@Service
@Transactional
public class HeritageWalkServiceImpl implements HeritageWalkService{

    private final Logger log = LoggerFactory.getLogger(HeritageWalkServiceImpl.class);
    
    @Inject
    private HeritageWalkRepository heritageWalkRepository;
    
    @Inject
    private HeritageWalkMapper heritageWalkMapper;
    
    /**
     * Save a heritageWalk.
     * @return the persisted entity
     */
    public HeritageWalkDTO save(HeritageWalkDTO heritageWalkDTO) {
        log.debug("Request to save HeritageWalk : {}", heritageWalkDTO);
        HeritageWalk heritageWalk = heritageWalkMapper.heritageWalkDTOToHeritageWalk(heritageWalkDTO);
        heritageWalk = heritageWalkRepository.save(heritageWalk);
        HeritageWalkDTO result = heritageWalkMapper.heritageWalkToHeritageWalkDTO(heritageWalk);
        return result;
    }

    /**
     *  get all the heritageWalks.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageWalk> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageWalks");
        Page<HeritageWalk> result = heritageWalkRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageWalk by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageWalkDTO findOne(Long id) {
        log.debug("Request to get HeritageWalk : {}", id);
        HeritageWalk heritageWalk = heritageWalkRepository.findOne(id);
        HeritageWalkDTO heritageWalkDTO = heritageWalkMapper.heritageWalkToHeritageWalkDTO(heritageWalk);
        return heritageWalkDTO;
    }

    /**
     *  delete the  heritageWalk by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageWalk : {}", id);
        heritageWalkRepository.delete(id);
    }
}

package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageSlideService;
import org.janastu.heritageapp.domain.HeritageSlide;
import org.janastu.heritageapp.repository.HeritageSlideRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageSlideDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageSlideMapper;
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
 * Service Implementation for managing HeritageSlide.
 */
@Service
@Transactional
public class HeritageSlideServiceImpl implements HeritageSlideService{

    private final Logger log = LoggerFactory.getLogger(HeritageSlideServiceImpl.class);
    
    @Inject
    private HeritageSlideRepository heritageSlideRepository;
    
    @Inject
    private HeritageSlideMapper heritageSlideMapper;
    
    /**
     * Save a heritageSlide.
     * @return the persisted entity
     */
    public HeritageSlideDTO save(HeritageSlideDTO heritageSlideDTO) {
        log.debug("Request to save HeritageSlide : {}", heritageSlideDTO);
        HeritageSlide heritageSlide = heritageSlideMapper.heritageSlideDTOToHeritageSlide(heritageSlideDTO);
        heritageSlide = heritageSlideRepository.save(heritageSlide);
        HeritageSlideDTO result = heritageSlideMapper.heritageSlideToHeritageSlideDTO(heritageSlide);
        return result;
    }

    /**
     *  get all the heritageSlides.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageSlide> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageSlides");
        Page<HeritageSlide> result = heritageSlideRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageSlide by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageSlideDTO findOne(Long id) {
        log.debug("Request to get HeritageSlide : {}", id);
        HeritageSlide heritageSlide = heritageSlideRepository.findOne(id);
        HeritageSlideDTO heritageSlideDTO = heritageSlideMapper.heritageSlideToHeritageSlideDTO(heritageSlide);
        return heritageSlideDTO;
    }

    /**
     *  delete the  heritageSlide by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageSlide : {}", id);
        heritageSlideRepository.delete(id);
    }
}

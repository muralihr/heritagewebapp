package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageGroupService;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.repository.HeritageGroupRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupMapper;
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
 * Service Implementation for managing HeritageGroup.
 */
@Service
@Transactional
public class HeritageGroupServiceImpl implements HeritageGroupService{

    private final Logger log = LoggerFactory.getLogger(HeritageGroupServiceImpl.class);
    
    @Inject
    private HeritageGroupRepository heritageGroupRepository;
    
    @Inject
    private HeritageGroupMapper heritageGroupMapper;
    
    /**
     * Save a heritageGroup.
     * @return the persisted entity
     */
    public HeritageGroupDTO save(HeritageGroupDTO heritageGroupDTO) {
        log.debug("Request to save HeritageGroup : {}", heritageGroupDTO);
        HeritageGroup heritageGroup = heritageGroupMapper.heritageGroupDTOToHeritageGroup(heritageGroupDTO);
        heritageGroup = heritageGroupRepository.save(heritageGroup);
        HeritageGroupDTO result = heritageGroupMapper.heritageGroupToHeritageGroupDTO(heritageGroup);
        return result;
    }

    /**
     *  get all the heritageGroups.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageGroup> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageGroups");
        Page<HeritageGroup> result = heritageGroupRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageGroup by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageGroupDTO findOne(Long id) {
        log.debug("Request to get HeritageGroup : {}", id);
        HeritageGroup heritageGroup = heritageGroupRepository.findOne(id);
        HeritageGroupDTO heritageGroupDTO = heritageGroupMapper.heritageGroupToHeritageGroupDTO(heritageGroup);
        return heritageGroupDTO;
    }

    /**
     *  delete the  heritageGroup by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageGroup : {}", id);
        heritageGroupRepository.delete(id);
    }
}

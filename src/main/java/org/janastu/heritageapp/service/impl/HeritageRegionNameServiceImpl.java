package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageRegionNameService;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.domain.HeritageRegionName;
import org.janastu.heritageapp.repository.HeritageRegionNameRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageRegionNameDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageRegionNameMapper;
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
 * Service Implementation for managing HeritageRegionName.
 */
@Service
@Transactional
public class HeritageRegionNameServiceImpl implements HeritageRegionNameService{

    private final Logger log = LoggerFactory.getLogger(HeritageRegionNameServiceImpl.class);
    
    @Inject
    private HeritageRegionNameRepository heritageRegionNameRepository;
    
    @Inject
    private HeritageRegionNameMapper heritageRegionNameMapper;
    
    /**
     * Save a heritageRegionName.
     * @return the persisted entity
     */
    public HeritageRegionNameDTO save(HeritageRegionNameDTO heritageRegionNameDTO) {
        log.debug("Request to save HeritageRegionName : {}", heritageRegionNameDTO);
        HeritageRegionName heritageRegionName = heritageRegionNameMapper.heritageRegionNameDTOToHeritageRegionName(heritageRegionNameDTO);
        heritageRegionName = heritageRegionNameRepository.save(heritageRegionName);
        HeritageRegionNameDTO result = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);
        return result;
    }

    /**
     *  get all the heritageRegionNames.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageRegionName> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageRegionNames");
        Page<HeritageRegionName> result = heritageRegionNameRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageRegionName by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageRegionNameDTO findOne(Long id) {
        log.debug("Request to get HeritageRegionName : {}", id);
        HeritageRegionName heritageRegionName = heritageRegionNameRepository.findOne(id);
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);
        return heritageRegionNameDTO;
    }

    /**
     *  delete the  heritageRegionName by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageRegionName : {}", id);
        heritageRegionNameRepository.delete(id);
    }
    
    @Override
	public List<HeritageRegionName> findAll() {
		// TODO Auto-generated method stub
		
		List<HeritageRegionName> result = heritageRegionNameRepository.findAll(); 
		return result ;
	}
}

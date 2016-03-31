package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageAppService;
import org.janastu.heritageapp.domain.HeritageApp;
import org.janastu.heritageapp.repository.HeritageAppRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageAppDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageAppMapper;
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
 * Service Implementation for managing HeritageApp.
 */
@Service
@Transactional
public class HeritageAppServiceImpl implements HeritageAppService{

    private final Logger log = LoggerFactory.getLogger(HeritageAppServiceImpl.class);
    
    @Inject
    private HeritageAppRepository heritageAppRepository;
    
    @Inject
    private HeritageAppMapper heritageAppMapper;
    
    /**
     * Save a heritageApp.
     * @return the persisted entity
     */
    public HeritageAppDTO save(HeritageAppDTO heritageAppDTO) {
        log.debug("Request to save HeritageApp : {}", heritageAppDTO);
        HeritageApp heritageApp = heritageAppMapper.heritageAppDTOToHeritageApp(heritageAppDTO);
        heritageApp = heritageAppRepository.save(heritageApp);
        HeritageAppDTO result = heritageAppMapper.heritageAppToHeritageAppDTO(heritageApp);
        return result;
    }

    /**
     *  get all the heritageApps.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageApp> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageApps");
        Page<HeritageApp> result = heritageAppRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageApp by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageAppDTO findOne(Long id) {
        log.debug("Request to get HeritageApp : {}", id);
        HeritageApp heritageApp = heritageAppRepository.findOneWithEagerRelationships(id);
        HeritageAppDTO heritageAppDTO = heritageAppMapper.heritageAppToHeritageAppDTO(heritageApp);
        return heritageAppDTO;
    }

    /**
     *  delete the  heritageApp by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageApp : {}", id);
        heritageAppRepository.delete(id);
    }

	@Override
	public HeritageAppDTO  findByName(String appId) {
		// TODO Auto-generated method stub
		
		HeritageApp app = heritageAppRepository.findByName(appId);
		
		HeritageAppDTO heritageAppDTO = heritageAppMapper.heritageAppToHeritageAppDTO(app);
        return heritageAppDTO;
	 
	}
}

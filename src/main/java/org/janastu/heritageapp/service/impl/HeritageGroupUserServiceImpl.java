package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageGroupUserService;
import org.janastu.heritageapp.domain.HeritageGroupUser;
import org.janastu.heritageapp.repository.HeritageGroupUserRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupUserDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupUserMapper;
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
 * Service Implementation for managing HeritageGroupUser.
 */
@Service
@Transactional
public class HeritageGroupUserServiceImpl implements HeritageGroupUserService{

    private final Logger log = LoggerFactory.getLogger(HeritageGroupUserServiceImpl.class);
    
    @Inject
    private HeritageGroupUserRepository heritageGroupUserRepository;
    
    @Inject
    private HeritageGroupUserMapper heritageGroupUserMapper;
    
    /**
     * Save a heritageGroupUser.
     * @return the persisted entity
     */
    public HeritageGroupUserDTO save(HeritageGroupUserDTO heritageGroupUserDTO) {
        log.debug("Request to save HeritageGroupUser : {}", heritageGroupUserDTO);
        HeritageGroupUser heritageGroupUser = heritageGroupUserMapper.heritageGroupUserDTOToHeritageGroupUser(heritageGroupUserDTO);
        heritageGroupUser = heritageGroupUserRepository.save(heritageGroupUser);
        HeritageGroupUserDTO result = heritageGroupUserMapper.heritageGroupUserToHeritageGroupUserDTO(heritageGroupUser);
        return result;
    }

    /**
     *  get all the heritageGroupUsers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageGroupUser> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageGroupUsers");
        Page<HeritageGroupUser> result = heritageGroupUserRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageGroupUser by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageGroupUserDTO findOne(Long id) {
        log.debug("Request to get HeritageGroupUser : {}", id);
        HeritageGroupUser heritageGroupUser = heritageGroupUserRepository.findOne(id);
        HeritageGroupUserDTO heritageGroupUserDTO = heritageGroupUserMapper.heritageGroupUserToHeritageGroupUserDTO(heritageGroupUser);
        return heritageGroupUserDTO;
    }

    /**
     *  delete the  heritageGroupUser by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageGroupUser : {}", id);
        heritageGroupUserRepository.delete(id);
    }
    
    public HeritageGroupUserDTO findByUserAndGroupId(Long userId, Long groupId) 
    {
    	Pageable pageable  = null;
    	 List<HeritageGroupUser> result = heritageGroupUserRepository.findAll( ); 
    	 HeritageGroupUser heritageGroupUser;
    	 for(HeritageGroupUser t : result)
    	 {
    		 if(t.getMember().getId() == userId && t.getGroup().getId() == groupId) 
    		 {
    			 heritageGroupUser = t;
    			 
    			 log.debug("heritageGroupUser fopund with userId/groupID "+  userId + "groupId"+ groupId);
    			 HeritageGroupUserDTO heritageGroupUserDTO = heritageGroupUserMapper.heritageGroupUserToHeritageGroupUserDTO(heritageGroupUser);
    			 return heritageGroupUserDTO;
    		 }
    		 
    	 }
        
         return null;
    }
}

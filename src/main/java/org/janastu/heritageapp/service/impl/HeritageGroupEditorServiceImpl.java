package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageGroupEditorService;
import org.janastu.heritageapp.domain.Authority;
import org.janastu.heritageapp.domain.HeritageGroupEditor;
import org.janastu.heritageapp.domain.User;
import org.janastu.heritageapp.repository.HeritageGroupEditorRepository;
import org.janastu.heritageapp.repository.UserRepository;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupEditorDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupEditorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HeritageGroupEditor.
 */
@Service
@Transactional
public class HeritageGroupEditorServiceImpl implements HeritageGroupEditorService{

    private final Logger log = LoggerFactory.getLogger(HeritageGroupEditorServiceImpl.class);
    
    @Inject
    private HeritageGroupEditorRepository heritageGroupEditorRepository;
    
    @Inject
    @Autowired
	private UserRepository userRepository;
    
    @Inject
    private HeritageGroupEditorMapper heritageGroupEditorMapper;
    
    /**
     * Save a heritageGroupEditor.
     * @return the persisted entity
     */
    public HeritageGroupEditorDTO save(HeritageGroupEditorDTO heritageGroupEditorDTO) {
        log.debug("Request to save HeritageGroupEditor : {}", heritageGroupEditorDTO);
        HeritageGroupEditor heritageGroupEditor = heritageGroupEditorMapper.heritageGroupEditorDTOToHeritageGroupEditor(heritageGroupEditorDTO);
        heritageGroupEditor = heritageGroupEditorRepository.save(heritageGroupEditor);
        //add additional authorities 
        Long userId = heritageGroupEditorDTO.getEditorId();
        User u = userRepository.findOne(userId);
         //= new TreeSet<Authority>();
         Set<Authority> authorities =  u.getAuthorities();
         Authority a = new Authority();
         a.setName("ROLE_EDITOR");
		 authorities.add(a);
		 u.setAuthorities(authorities);
        
        //update the user field as well add role editor ;
        
        HeritageGroupEditorDTO result = heritageGroupEditorMapper.heritageGroupEditorToHeritageGroupEditorDTO(heritageGroupEditor);
        return result;
    }

    /**
     *  get all the heritageGroupEditors.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageGroupEditor> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageGroupEditors");
        Page<HeritageGroupEditor> result = heritageGroupEditorRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageGroupEditor by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageGroupEditorDTO findOne(Long id) {
        log.debug("Request to get HeritageGroupEditor : {}", id);
        HeritageGroupEditor heritageGroupEditor = heritageGroupEditorRepository.findOne(id);
        HeritageGroupEditorDTO heritageGroupEditorDTO = heritageGroupEditorMapper.heritageGroupEditorToHeritageGroupEditorDTO(heritageGroupEditor);
        return heritageGroupEditorDTO;
    }

    /**
     *  delete the  heritageGroupEditor by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageGroupEditor : {}", id);
        heritageGroupEditorRepository.delete(id);
    }
}

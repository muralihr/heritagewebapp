package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.TextGeoTagHeritageEntityService;
import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.TextGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.TextGeoTagHeritageEntityMapper;
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
 * Service Implementation for managing TextGeoTagHeritageEntity.
 */
@Service
@Transactional
public class TextGeoTagHeritageEntityServiceImpl implements TextGeoTagHeritageEntityService{

    private final Logger log = LoggerFactory.getLogger(TextGeoTagHeritageEntityServiceImpl.class);
    
    @Inject
    private TextGeoTagHeritageEntityRepository textGeoTagHeritageEntityRepository;
    
    @Inject
    private TextGeoTagHeritageEntityMapper textGeoTagHeritageEntityMapper;
    
    /**
     * Save a textGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public TextGeoTagHeritageEntityDTO save(TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO) {
        log.debug("Request to save TextGeoTagHeritageEntity : {}", textGeoTagHeritageEntityDTO);
        TextGeoTagHeritageEntity textGeoTagHeritageEntity = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityDTOToTextGeoTagHeritageEntity(textGeoTagHeritageEntityDTO);
        textGeoTagHeritageEntity = textGeoTagHeritageEntityRepository.save(textGeoTagHeritageEntity);
        TextGeoTagHeritageEntityDTO result = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);
        return result;
    }

    /**
     *  get all the textGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TextGeoTagHeritageEntity> findAll(Pageable pageable) {
        log.debug("Request to get all TextGeoTagHeritageEntitys");
        Page<TextGeoTagHeritageEntity> result = textGeoTagHeritageEntityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one textGeoTagHeritageEntity by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TextGeoTagHeritageEntityDTO findOne(Long id) {
        log.debug("Request to get TextGeoTagHeritageEntity : {}", id);
        TextGeoTagHeritageEntity textGeoTagHeritageEntity = textGeoTagHeritageEntityRepository.findOne(id);
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);
        return textGeoTagHeritageEntityDTO;
    }

    /**
     *  delete the  textGeoTagHeritageEntity by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TextGeoTagHeritageEntity : {}", id);
        textGeoTagHeritageEntityRepository.delete(id);
    }

	@Override
	public List<TextGeoTagHeritageEntity> findAllAsAList() {
		// TODO Auto-generated method stub
		return null;
	}
}

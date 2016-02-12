package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.ImageGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.web.rest.AppConstants;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.ImageGeoTagHeritageEntityMapper;
import org.janastu.heritageapp.web.rest.util.HeritageFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
/**
 * Service Implementation for managing ImageGeoTagHeritageEntity.
 */
@Service
@Transactional
public class ImageGeoTagHeritageEntityServiceImpl implements ImageGeoTagHeritageEntityService{

    private final Logger log = LoggerFactory.getLogger(ImageGeoTagHeritageEntityServiceImpl.class);
    
    @Inject
    private ImageGeoTagHeritageEntityRepository imageGeoTagHeritageEntityRepository;
    
    @Inject
    private ImageGeoTagHeritageEntityMapper imageGeoTagHeritageEntityMapper;

	@Autowired
	private Environment environment;
    
    /**
     * Save a imageGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public ImageGeoTagHeritageEntityDTO save(ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO) {
        log.debug("Request to save ImageGeoTagHeritageEntity : {}", imageGeoTagHeritageEntityDTO);
        ImageGeoTagHeritageEntity imageGeoTagHeritageEntity = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityDTOToImageGeoTagHeritageEntity(imageGeoTagHeritageEntityDTO);
        imageGeoTagHeritageEntity = imageGeoTagHeritageEntityRepository.save(imageGeoTagHeritageEntity);
        ImageGeoTagHeritageEntityDTO result = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);
        return result;
    }

    /**
     *  get all the imageGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ImageGeoTagHeritageEntity> findAll(Pageable pageable) {
        log.debug("Request to get all ImageGeoTagHeritageEntitys");
        Page<ImageGeoTagHeritageEntity> result = imageGeoTagHeritageEntityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one imageGeoTagHeritageEntity by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ImageGeoTagHeritageEntityDTO findOne(Long id) {
        log.debug("Request to get ImageGeoTagHeritageEntity : {}", id);
        ImageGeoTagHeritageEntity imageGeoTagHeritageEntity = imageGeoTagHeritageEntityRepository.findOne(id);
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);
        return imageGeoTagHeritageEntityDTO;
    }
    
    

    /**
     *  delete the  imageGeoTagHeritageEntity by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImageGeoTagHeritageEntity : {}", id);
        
        ImageGeoTagHeritageEntity imageGeoTagHeritageEntity = imageGeoTagHeritageEntityRepository.findOne(id);
        
        String url = imageGeoTagHeritageEntity.getUrlOrfileLink();
        
        HeritageFileUtil fUtil  = new  HeritageFileUtil();
    	log.debug("DIR PATA HOME" + environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV));
		String pataHomeVar = environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV);
        fUtil.getRootFolderName(pataHomeVar);
        
        String filename ="";
		try {
			filename = Paths.get(new URI(url).getPath()).getFileName().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        fUtil.deleteFile("IMAGE",filename);      	
        
        
        imageGeoTagHeritageEntityRepository.delete(id);
    }
    
    public List<ImageGeoTagHeritageEntity> findAllAsAList()
    {    	
    	//
    	return imageGeoTagHeritageEntityRepository.findAllByOrderByIdAsc();
    }
}

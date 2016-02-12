package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.VideoGeoTagHeritageEntityService;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.VideoGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.web.rest.AppConstants;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.VideoGeoTagHeritageEntityMapper;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VideoGeoTagHeritageEntity.
 */
@Service
@Transactional
public class VideoGeoTagHeritageEntityServiceImpl implements VideoGeoTagHeritageEntityService{

    private final Logger log = LoggerFactory.getLogger(VideoGeoTagHeritageEntityServiceImpl.class);
    
    @Inject
    private VideoGeoTagHeritageEntityRepository videoGeoTagHeritageEntityRepository;
    
    @Inject
    private VideoGeoTagHeritageEntityMapper videoGeoTagHeritageEntityMapper;
	@Autowired
	private Environment environment;
    
    /**
     * Save a videoGeoTagHeritageEntity.
     * @return the persisted entity
     */
    public VideoGeoTagHeritageEntityDTO save(VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO) {
        log.debug("Request to save VideoGeoTagHeritageEntity : {}", videoGeoTagHeritageEntityDTO);
        VideoGeoTagHeritageEntity videoGeoTagHeritageEntity = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityDTOToVideoGeoTagHeritageEntity(videoGeoTagHeritageEntityDTO);
        videoGeoTagHeritageEntity = videoGeoTagHeritageEntityRepository.save(videoGeoTagHeritageEntity);
        VideoGeoTagHeritageEntityDTO result = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);
        return result;
    }

    /**
     *  get all the videoGeoTagHeritageEntitys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<VideoGeoTagHeritageEntity> findAll(Pageable pageable) {
        log.debug("Request to get all VideoGeoTagHeritageEntitys");
        Page<VideoGeoTagHeritageEntity> result = videoGeoTagHeritageEntityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one videoGeoTagHeritageEntity by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VideoGeoTagHeritageEntityDTO findOne(Long id) {
        log.debug("Request to get VideoGeoTagHeritageEntity : {}", id);
        VideoGeoTagHeritageEntity videoGeoTagHeritageEntity = videoGeoTagHeritageEntityRepository.findOne(id);
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);
        return videoGeoTagHeritageEntityDTO;
    }

    /**
     *  delete the  videoGeoTagHeritageEntity by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete VideoGeoTagHeritageEntity : {}", id);
        
        VideoGeoTagHeritageEntity videoGeoTagHeritageEntity = videoGeoTagHeritageEntityRepository.findOne(id);
        
        String url = videoGeoTagHeritageEntity.getUrlOrfileLink();
        
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
        
        fUtil.deleteFile("VIDEO",filename);      	
        
        
        videoGeoTagHeritageEntityRepository.delete(id);
    }

	@Override
	public List<VideoGeoTagHeritageEntity> findAllAsAList() {
		// TODO Auto-generated method stub
		return videoGeoTagHeritageEntityRepository.findAllByOrderByIdAsc();
	}
}

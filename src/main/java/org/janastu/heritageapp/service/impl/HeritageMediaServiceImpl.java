package org.janastu.heritageapp.service.impl;

import org.janastu.heritageapp.service.HeritageMediaService;
import org.janastu.heritageapp.domain.HeritageMedia;
import org.janastu.heritageapp.repository.HeritageMediaRepository;
import org.janastu.heritageapp.repository.UserRepository;
import org.janastu.heritageapp.security.SecurityUtils;
import org.janastu.heritageapp.web.rest.AppConstants;
 
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageMediaMapper;
import org.janastu.heritageapp.web.rest.util.OSValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HeritageMedia.
 */
@Service
@Transactional
public class HeritageMediaServiceImpl implements HeritageMediaService{

    private final Logger log = LoggerFactory.getLogger(HeritageMediaServiceImpl.class);
    
    @Inject
    private HeritageMediaRepository heritageMediaRepository;
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private HeritageMediaMapper heritageMediaMapper;
	@Autowired
	private Environment environment;
    /**
     * Save a heritageMedia.
     * @return the persisted entity
     */
    public HeritageMediaDTO save(HeritageMediaDTO heritageMediaDTO) {
        log.debug("Request to save HeritageMedia : {}", heritageMediaDTO);
        
        ///move the file to a specific folder 
        //get http link if not found move the file - 
    	byte[] mediaFile = new byte[1];
        if(heritageMediaDTO.getUrlOrfileLink().startsWith("http"))
        {
        	
        	log.debug("URL LINK check start with http to save HeritageMedia : {}", heritageMediaDTO.getUrlOrfileLink());
    
			heritageMediaDTO.setMediaFile(mediaFile);
        }
        else
        {
        	log.debug("URL LINK check does not start with http to save HeritageMedia : {}", heritageMediaDTO.getUrlOrfileLink());
         	//String urlLinkToMedia = copyMediaFile(heritageMediaDTO);;
        	heritageMediaDTO.setMediaFile(mediaFile);     
			//urlLinkToMedia
        	heritageMediaDTO.setUrlOrfileLink(heritageMediaDTO.getUrlOrfileLink());    	
        	
        }
        
    //   org.janastu.heritageapp.domain.User u = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        
        Long userID = heritageMediaDTO.getUserId();
       heritageMediaDTO.setUserId(userID);
       heritageMediaDTO.setUserLogin(heritageMediaDTO.getUserLogin());
        
        HeritageMedia heritageMedia = heritageMediaMapper.heritageMediaDTOToHeritageMedia(heritageMediaDTO);
        
        ///
        heritageMedia = heritageMediaRepository.save(heritageMedia);
        HeritageMediaDTO result = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);
        return result;
    }
    
    public HeritageMediaDTO saveMobile(HeritageMediaDTO heritageMediaDTO) {
        log.debug("Request to save HeritageMedia : {}", heritageMediaDTO);
        
        ///move the file to a specific folder 
        //get http link if not found move the file - 
    	/*byte[] mediaFile = new byte[1];
        if(heritageMediaDTO.getUrlOrfileLink().startsWith("http"))
        {
    
			heritageMediaDTO.setMediaFile(mediaFile);
        }
        else
        {
         	String urlLinkToMedia = copyMediaFile(heritageMediaDTO);;
        	heritageMediaDTO.setMediaFile(mediaFile);       
			//urlLinkToMedia
        	heritageMediaDTO.setUrlOrfileLink(urlLinkToMedia);              	
        	
        }*/
        HeritageMedia heritageMedia = heritageMediaMapper.heritageMediaDTOToHeritageMedia(heritageMediaDTO);
        
        ///
        heritageMedia = heritageMediaRepository.save(heritageMedia);
        HeritageMediaDTO result = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);
        log.debug("save mobile}", heritageMediaDTO);
        return result;
    }

    /**
     *  get all the heritageMedias.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HeritageMedia> findAll(Pageable pageable) {
        log.debug("Request to get all HeritageMedias");
        Page<HeritageMedia> result = heritageMediaRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one heritageMedia by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HeritageMediaDTO findOne(Long id) {
        log.debug("Request to get HeritageMedia : {}", id);
        HeritageMedia heritageMedia = heritageMediaRepository.findOne(id);
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);
        return heritageMediaDTO;
    }

    /**
     *  delete the  heritageMedia by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HeritageMedia : {}", id);
        heritageMediaRepository.delete(id);
    }
    
    public String copyMediaFile(HeritageMediaDTO heritageMediaDTO)
    {
    	
    	

		int mediaType = heritageMediaDTO.getMediaType();		
		String mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
		String pataHome = environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV);
		String newFilename = heritageMediaDTO.getUrlOrfileLink();	
		String urlLinkToMedia = null;
		String storageDirectory = "";
		if (pataHome == null) {
			if (OSValidator.isUnix()) {
				pataHome = AppConstants.UPLOAD_FOLDER_LINUX;
				log.debug("UNIX ENV"+ pataHome);
			}
			if (OSValidator.isWindows()) {
				pataHome = AppConstants.UPLOAD_FOLDER_WIN;
				log.debug("WINDOWS ENV" +pataHome);
			}

		}

		if (OSValidator.isUnix()) {
			mediaServerUrl = AppConstants.MEDIA_SERVER_URL_UBUNTU;
			log.debug("UNIX ENV");
		}
		if (OSValidator.isWindows()) {
			mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
			log.debug("WINDOWS ENV");
		}

		log.debug("UPLOAD  FOLDER LOCATION " + pataHome);

		switch (mediaType) {
		case AppConstants.AUDIOTYPE:
			storageDirectory = pataHome + "/" + AppConstants.UPLOAD_FOLDER_AUDIO;
			urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
					+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_AUDIO;
			break;
		case AppConstants.IMAGETYPE:
			storageDirectory = pataHome + "/" + AppConstants.UPLOAD_FOLDER_IMAGES;
			urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
					+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_IMAGES;
			break;
		case AppConstants.TEXTTYPE:
			// No upload just store ;
			break;
		case AppConstants.VIDEOTYPE:
			storageDirectory = pataHome + "/" + AppConstants.UPLOAD_FOLDER_VIDEO;
			urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
					+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_VIDEO;
			break;
		}

		String downLoadFileName = storageDirectory + "/" + heritageMediaDTO.getUrlOrfileLink();;
		String contentType = heritageMediaDTO.getMediaFileContentType();
		// directory exists - no create directory ;

		if (heritageMediaDTO.getMediaFile().length != 0 ) {
			try {
				byte[] bytes = heritageMediaDTO.getMediaFile();
				// Creating the directory to store file
				// Create the file on server
				File serverFile = new File(downLoadFileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				log.debug("Server File Location=" + serverFile.getAbsolutePath());

			} catch (Exception e) {
				 
			}
		} else {
			
			//file is empty 
			 
		}

    	
    	return urlLinkToMedia + "/"+heritageMediaDTO.getUrlOrfileLink() ;
    }

	@Override
	public List<HeritageMedia> findAllAsAList() {
		// TODO Auto-generated method stub
		return heritageMediaRepository.findAllByOrderByIdAsc();
	}
}

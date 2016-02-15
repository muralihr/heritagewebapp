package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.apache.commons.io.FileUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.AudioGeoTagHeritageEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.janastu.heritageapp.web.rest.util.OSValidator;
/**
 * REST controller for managing AudioGeoTagHeritageEntity.
 */
@RestController
@RequestMapping("/api")
public class AudioGeoTagHeritageEntityResource {

    private final Logger log = LoggerFactory.getLogger(AudioGeoTagHeritageEntityResource.class);
        
    @Inject
    private AudioGeoTagHeritageEntityService audioGeoTagHeritageEntityService;
    
    @Inject
    private AudioGeoTagHeritageEntityMapper audioGeoTagHeritageEntityMapper;

    @Autowired
    private Environment environment;
    /**
     * POST  /audioGeoTagHeritageEntitys -> Create a new audioGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/audioGeoTagHeritageEntitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AudioGeoTagHeritageEntityDTO> createAudioGeoTagHeritageEntity(@Valid @RequestBody AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to save AudioGeoTagHeritageEntity : {}", audioGeoTagHeritageEntityDTO);
        if (audioGeoTagHeritageEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("audioGeoTagHeritageEntity", "idexists", "A new audioGeoTagHeritageEntity cannot already have an ID")).body(null);
        }
      
        
        
        ///
        //get the file and dump the data 
        ///
        String link;
        if(audioGeoTagHeritageEntityDTO.getUrlOrfileLink().startsWith("http"))
        {        
        	link = audioGeoTagHeritageEntityDTO.getUrlOrfileLink();
        }
        else
        {
        	link = transferFile(audioGeoTagHeritageEntityDTO);
        }
        
        audioGeoTagHeritageEntityDTO.setUrlOrfileLink(link);
        byte[] photo = new byte[1];
        audioGeoTagHeritageEntityDTO.setAudioFile( photo);
        
        AudioGeoTagHeritageEntityDTO result = audioGeoTagHeritageEntityService.save(audioGeoTagHeritageEntityDTO);
        return ResponseEntity.created(new URI("/api/imageGeoTagHeritageEntitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("imageGeoTagHeritageEntity", result.getId().toString()))
            .body(result);
    
    }
    
    private String transferFile(AudioGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO) {
    	
        
    	  log.debug( "DIR PATA HOME" +environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV ));
    	  String pataHome = environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV);
    	  
    	   String mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
	  		if(pataHome == null)
	  		{
	  			if(OSValidator.isUnix())
	  			{
	  				pataHome = AppConstants.UPLOAD_FOLDER_LINUX;
	  				log.debug("UNIX ENV");
	  			}
	  			if(OSValidator.isWindows())
	  			{
	  				pataHome = AppConstants.UPLOAD_FOLDER_WIN;
	  				log.debug("WINDOWS ENV");
	  			}
	  				
	  		}
	  		
	  		if(OSValidator.isUnix())
	  		{
	  			mediaServerUrl = AppConstants.MEDIA_SERVER_URL_UBUNTU;
	  			log.debug("UNIX ENV");
	  		}
	  		if(OSValidator.isWindows())
	  		{
	  			mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
	  			log.debug("WINDOWS ENV");
	  		}
    	  
    	 	String storageDirectory = pataHome +"//" + AppConstants.UPLOAD_FOLDER_IMAGES;
    	 	String 	urlLinkToMedia = mediaServerUrl +"/"+AppConstants.MEDIA_APP_NAME +"/"+ AppConstants.MEDIA_ROOT_FOLDER_NAME +"/" +AppConstants.UPLOAD_FOLDER_IMAGES;
    	 	   String downLoadFileName = storageDirectory +"//"+ videoGeoTagHeritageEntityDTO.getUrlOrfileLink();
    	    
    	    try {
  				File newFile  = new File( downLoadFileName);
  				
  				log.debug( "uploaded file to PATH" +newFile.getAbsolutePath());
                    
                 FileUtils.writeByteArrayToFile(newFile, videoGeoTagHeritageEntityDTO.getAudioFile() );
                 
             } catch(Exception e){}
    	    String link = (urlLinkToMedia + "/"+videoGeoTagHeritageEntityDTO.getUrlOrfileLink());
  		// TODO Auto-generated method stub
  		return link;
  	}

    /**
     * PUT  /audioGeoTagHeritageEntitys -> Updates an existing audioGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/audioGeoTagHeritageEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AudioGeoTagHeritageEntityDTO> updateAudioGeoTagHeritageEntity(@Valid @RequestBody AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to update AudioGeoTagHeritageEntity : {}", audioGeoTagHeritageEntityDTO);
        if (audioGeoTagHeritageEntityDTO.getId() == null) {
            return createAudioGeoTagHeritageEntity(audioGeoTagHeritageEntityDTO);
        }
        AudioGeoTagHeritageEntityDTO result = audioGeoTagHeritageEntityService.save(audioGeoTagHeritageEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("audioGeoTagHeritageEntity", audioGeoTagHeritageEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audioGeoTagHeritageEntitys -> get all the audioGeoTagHeritageEntitys.
     */
    @RequestMapping(value = "/audioGeoTagHeritageEntitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<AudioGeoTagHeritageEntityDTO>> getAllAudioGeoTagHeritageEntitys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AudioGeoTagHeritageEntitys");
        Page<AudioGeoTagHeritageEntity> page = audioGeoTagHeritageEntityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audioGeoTagHeritageEntitys");
        return new ResponseEntity<>(page.getContent().stream()
            .map(audioGeoTagHeritageEntityMapper::audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /audioGeoTagHeritageEntitys/:id -> get the "id" audioGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/audioGeoTagHeritageEntitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AudioGeoTagHeritageEntityDTO> getAudioGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to get AudioGeoTagHeritageEntity : {}", id);
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityService.findOne(id);
        return Optional.ofNullable(audioGeoTagHeritageEntityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /audioGeoTagHeritageEntitys/:id -> delete the "id" audioGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/audioGeoTagHeritageEntitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAudioGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to delete AudioGeoTagHeritageEntity : {}", id);
        audioGeoTagHeritageEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("audioGeoTagHeritageEntity", id.toString())).build();
    }
    
    @RequestMapping(value = "/audioGeoTagHeritageEntitysGeoJson",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        @Transactional(readOnly = true)
        public  FeatureCollection  getAllAudioGeoTagHeritageEntitysAsGeoJSON( )
            throws URISyntaxException {
        	
         
            log.debug("REST request to get a page of audioGeoTagHeritageEntityService All as list");
            List<AudioGeoTagHeritageEntity> page = audioGeoTagHeritageEntityService.findAllAsAList();
            
            /*
             * We create a FeatureCollection into which we will put each Feature  with Geometry Objects
             */
            FeatureCollection collection = new FeatureCollection();
            
     
            for(AudioGeoTagHeritageEntity item: page)
            {
            	
            	LngLatAlt coordinates = new LngLatAlt(item.getLongitude() ,item.getLatitude());
            	Point c = new Point(coordinates);        	
            	Feature f = new Feature();
            	f.setGeometry(c);
            	f.setId(item.getId().toString());
            	Map<String, Object> properties = new HashMap<String, Object>();
            	
            	properties.put("title",item.getTitle());
            	properties.put("description",item.getDescription());
            	properties.put("url",item.getUrlOrfileLink());
            	properties.put("marker-color","#ff8888");
            	properties.put("marker-color","#ff8888");
            	properties.put("marker-size","small");
            	//marker-size
            	//"marker-color": "#ff8888",
            	
            	f.setProperties(properties);        	
            	collection.add(f);
            	
            } 
            
            return  collection;
            
        }
}

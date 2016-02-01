package org.janastu.heritageapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.HeritageCategoryRepository;
import org.janastu.heritageapp.repository.HeritageLanguageRepository;
import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.ImageGeoTagHeritageEntityMapper;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
 
import java.util.Map;
import java.util.UUID;

import com.codahale.metrics.annotation.Timed;

 

/**
 * REST controller for managing ImageGeoTagHeritageEntity.
 */
@RestController
@RequestMapping("/api")
public class ImageGeoTagHeritageEntityResource {

    private final Logger log = LoggerFactory.getLogger(ImageGeoTagHeritageEntityResource.class);
    private String fileUploadDirectory ="imagefiles";
    @Inject
    private ImageGeoTagHeritageEntityService imageGeoTagHeritageEntityService;
    
    @Inject
    private ImageGeoTagHeritageEntityMapper imageGeoTagHeritageEntityMapper;
    
    @Inject
    HeritageCategoryRepository heritageCategoryRepository;

    @Inject
    HeritageLanguageRepository heritageLanguageRepository;
    
    
    @Autowired
    private Environment environment;
	 
    
    /**
     * POST  /imageGeoTagHeritageEntitys -> Create a new imageGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/imageGeoTagHeritageEntitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageGeoTagHeritageEntityDTO> createImageGeoTagHeritageEntity(@Valid @RequestBody ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to save ImageGeoTagHeritageEntity : {}", imageGeoTagHeritageEntityDTO);
        if (imageGeoTagHeritageEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imageGeoTagHeritageEntity", "idexists", "A new imageGeoTagHeritageEntity cannot already have an ID")).body(null);
        }
        ImageGeoTagHeritageEntityDTO result = imageGeoTagHeritageEntityService.save(imageGeoTagHeritageEntityDTO);
        return ResponseEntity.created(new URI("/api/imageGeoTagHeritageEntitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("imageGeoTagHeritageEntity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imageGeoTagHeritageEntitys -> Updates an existing imageGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/imageGeoTagHeritageEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageGeoTagHeritageEntityDTO> updateImageGeoTagHeritageEntity(@Valid @RequestBody ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to update ImageGeoTagHeritageEntity : {}", imageGeoTagHeritageEntityDTO);
        if (imageGeoTagHeritageEntityDTO.getId() == null) {
            return createImageGeoTagHeritageEntity(imageGeoTagHeritageEntityDTO);
        }
        ImageGeoTagHeritageEntityDTO result = imageGeoTagHeritageEntityService.save(imageGeoTagHeritageEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("imageGeoTagHeritageEntity", imageGeoTagHeritageEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imageGeoTagHeritageEntitys -> get all the imageGeoTagHeritageEntitys.
     */
    @RequestMapping(value = "/imageGeoTagHeritageEntitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ImageGeoTagHeritageEntityDTO>> getAllImageGeoTagHeritageEntitys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ImageGeoTagHeritageEntitys");
        Page<ImageGeoTagHeritageEntity> page = imageGeoTagHeritageEntityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/imageGeoTagHeritageEntitys");
        return new ResponseEntity<>(page.getContent().stream()
            .map(imageGeoTagHeritageEntityMapper::imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /imageGeoTagHeritageEntitys/:id -> get the "id" imageGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/imageGeoTagHeritageEntitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageGeoTagHeritageEntityDTO> getImageGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to get ImageGeoTagHeritageEntity : {}", id);
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityService.findOne(id);
        
         
        
       
        return Optional.ofNullable(imageGeoTagHeritageEntityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @RequestMapping(value = "/imageGeoTagHeritageEntitys/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public ResponseEntity<ImageGeoTagHeritageEntityDTO> getImageGeoTagHeritageEntity2(@PathVariable Long id) {
            log.debug("REST request to get ImageGeoTagHeritageEntity : {}", id);
            ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityService.findOne(id);
            return Optional.ofNullable(imageGeoTagHeritageEntityDTO)
                .map(result -> new ResponseEntity<>(
                    result,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    /**
     * DELETE  /imageGeoTagHeritageEntitys/:id -> delete the "id" imageGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/imageGeoTagHeritageEntitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteImageGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to delete ImageGeoTagHeritageEntity : {}", id);
        imageGeoTagHeritageEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("imageGeoTagHeritageEntity", id.toString())).build();
    }
    
    

    //imageGeoTagHeritageFromMobile
    @RequestMapping(value = "/imageGeoTagHeritageFromMobile",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImageGeoTagHeritageEntityDTO> createImageGeoTagHeritageEntityFromMobile(MultipartHttpServletRequest request, HttpServletResponse response ) throws URISyntaxException 
    {
      	  log.debug("uploadPost called title" + request.getParameter("title"));
      	  log.debug("description  "+   request.getParameter("description"));
      	log.debug("category  "+   request.getParameter("category"));
      	log.debug("language  "+   request.getParameter("language"));
      	log.debug("latitude  "+   request.getParameter("latitude"));
      	log.debug("longitude  "+   request.getParameter("longitude"));
      	log.debug("mediatype  "+   request.getParameter("mediatype"));
      	
    	String title = request.getParameter("title");
      	String description = request.getParameter("description");
      	
      	String language = request.getParameter("language");
      	String category = request.getParameter("category");
    	String address = request.getParameter("address");
      	
      	String latitude = request.getParameter("latitude");
      	String longitude = request.getParameter("longitude");
      	ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = null;
             
    	  log.debug( "DIR CATALINA_HOME" +environment.getProperty("CATALINA_HOMEA"));
    	  String catalinahome = environment.getProperty("CATALINA_HOMEA");
          Iterator<String> itr = request.getFileNames();
          MultipartFile mpf = null;
          byte[] photo  = null;
             
             File newFile = null;
             while (itr.hasNext()) {
             	 
                 mpf = request.getFile(itr.next());
                 log.debug("Uploading {}", mpf.getOriginalFilename());
                 
                 String newFilenameBase = mpf.getOriginalFilename();
                 String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
              //   String newFilename = newFilenameBase + originalFileExtension;
     		     String newFilename =    mpf.getOriginalFilename();
                // String storageDirectory = fileUploadDirectory;                 
     		    String storageDirectory =  catalinahome +"//webapps//"+"heritagedocs//list//heritageaudio";
     		    
     		    String downLoadFileName = storageDirectory +"//"+ mpf.getOriginalFilename();
                String contentType = mpf.getContentType();
                //directory exists - no create directory ;                 
                 File theDir = new File(storageDirectory);                 
                
              // if the directory does not exist, create it
 	             if (!theDir.exists()) {
 	                 log.debug("creating directory: " + storageDirectory);
 	                 boolean result = false;
 	
 	                 try{
 	                     theDir.mkdir();
 	                     result = true;
 	                 } 
 	                 catch(SecurityException se){
 	                     //handle it
 	                 }        
 	                 if(result) {    
 	                	 log.debug( "DIR created" +storageDirectory);  
 	                 }
 	             }
                 
     			 
                 try {
     				newFile  = new File( downLoadFileName);
     				
     				log.debug( "uploaded file to PATH" +newFile.getAbsolutePath());
                     mpf.transferTo(newFile);   
                     photo = FileUtils.readFileToByteArray(newFile);
                 }    catch(FileNotFoundException e) {
    				 log.error("Could not upload file "+newFile, e);
                  
                 }
     			catch(IOException e) {
                     log.error("Could not upload file "+mpf.getOriginalFilename(), e);
                 }
             }
         ///check 

             
            // 
            imageGeoTagHeritageEntityDTO = new ImageGeoTagHeritageEntityDTO();
            Long l1 = new Long(1);
            //imageGeoTagHeritageEntityDTO.setId(l1);
            imageGeoTagHeritageEntityDTO.setAddress(address);
            imageGeoTagHeritageEntityDTO.setLatitude(Double.valueOf( latitude));
            imageGeoTagHeritageEntityDTO.setLongitude( Double.valueOf( longitude));
            imageGeoTagHeritageEntityDTO.setTitle(title);
            
            
            imageGeoTagHeritageEntityDTO.setDescription("Descr:"+description);
            
            HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
            if(hCategory != null)
            imageGeoTagHeritageEntityDTO.setHeritageCategoryId(hCategory.getId());
            HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);
            if(hLanguage != null)
            imageGeoTagHeritageEntityDTO.setHeritageLanguageId(hLanguage.getId());

            imageGeoTagHeritageEntityDTO.setUrlOrfileLink("heritagedocs//list//heritageaudio//"+mpf.getOriginalFilename());
           // imageGeoTagHeritageEntityDTO.setPhoto(photo);
            byte [] array = new byte[1];
            imageGeoTagHeritageEntityDTO.setPhoto(array);
            
            ImageGeoTagHeritageEntityDTO result = imageGeoTagHeritageEntityService.save(imageGeoTagHeritageEntityDTO);
            return ResponseEntity.created(new URI("/api/imageGeoTagHeritageEntitys/" + 1))
                .headers(HeaderUtil.createEntityCreationAlert("imageGeoTagHeritageEntity", "1"))
                .body(imageGeoTagHeritageEntityDTO);
    	 
    	}
    
    

    @RequestMapping(value = "/imageGeoTagHeritageEntitysGeoJson",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public  FeatureCollection  getAllImageGeoTagHeritageEntitysAsGeoJSON( )
        throws URISyntaxException {
    	
     
        log.debug("REST request to get a page of ImageGeoTagHeritageEntitys");
        List<ImageGeoTagHeritageEntity> page = imageGeoTagHeritageEntityService.findAllAsAList();
        
        /*
         * We create a FeatureCollection into which we will put each Feature  with Geometry Objects
         */
        FeatureCollection collection = new FeatureCollection();
        
 
        for(ImageGeoTagHeritageEntity item: page)
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

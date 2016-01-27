package org.janastu.heritageapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
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
      	
       
    	 
    	  ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = null;
             
    	  log.debug( "DIR CATALINA_HOME" +environment.getProperty("CATALINA_HOMEA"));
    	  String catalinahome = environment.getProperty("CATALINA_HOMEA");
          Iterator<String> itr = request.getFileNames();
          MultipartFile mpf;
             
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
                 }    catch(FileNotFoundException e) {
    				 log.error("Could not upload file "+newFile, e);
                  
                 }
     			catch(IOException e) {
                     log.error("Could not upload file "+mpf.getOriginalFilename(), e);
                 }
             }
         ///check 
             RestTemplate restTemplate = new RestTemplate();
             HttpHeaders header = new HttpHeaders();
             MappingJackson2HttpMessageConverter c;
             header.set("Connection", "Close");

             header.setContentType(MediaType.MULTIPART_FORM_DATA);

             try {

                 MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
                 parts.add("title", "");
                 parts.add("description", "");
                 parts.add("category", "");
                 parts.add("language", "");
                 parts.add("latitude", "");
                 parts.add("longitude", "");
                 parts.add("mediatype", "");



              //   parts.add("picture", new FileSystemResource(mpictureFile));

          //       RestTemplate restTemplate = new RestTemplate();
                 restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                 restTemplate.getMessageConverters().add(
                         new FormHttpMessageConverter());
                 restTemplate.getMessageConverters().add(
                         new StringHttpMessageConverter());
                 HttpEntity<Object> request2 = new HttpEntity<Object>(parts, header);
                 MimeType m;
                // restTemplate.postForLocation("", request2);
                 //

             //    Log.d("", "Calling REST to upload file");

            //     Log.d("", "The reaponse is: "+restTemplate.postForLocation(url, request2));

             } catch (Exception e) {
              

             }
             
            // 
            imageGeoTagHeritageEntityDTO = new ImageGeoTagHeritageEntityDTO();
            Long l1 = new Long(22);
            imageGeoTagHeritageEntityDTO.setId(l1);
            imageGeoTagHeritageEntityDTO.setAddress("sss");
            
           // ImageGeoTagHeritageEntityDTO result = imageGeoTagHeritageEntityService.save(imageGeoTagHeritageEntityDTO);
            return ResponseEntity.created(new URI("/api/imageGeoTagHeritageEntitys/" + 1))
                .headers(HeaderUtil.createEntityCreationAlert("imageGeoTagHeritageEntity", "1"))
                .body(imageGeoTagHeritageEntityDTO);
    	 
    	}

}

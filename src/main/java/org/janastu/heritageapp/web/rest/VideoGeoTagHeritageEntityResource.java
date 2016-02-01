package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.service.VideoGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.VideoGeoTagHeritageEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing VideoGeoTagHeritageEntity.
 */
@RestController
@RequestMapping("/api")
public class VideoGeoTagHeritageEntityResource {

    private final Logger log = LoggerFactory.getLogger(VideoGeoTagHeritageEntityResource.class);
        
    @Inject
    private VideoGeoTagHeritageEntityService videoGeoTagHeritageEntityService;
    
    @Inject
    private VideoGeoTagHeritageEntityMapper videoGeoTagHeritageEntityMapper;
    
    /**
     * POST  /videoGeoTagHeritageEntitys -> Create a new videoGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/videoGeoTagHeritageEntitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VideoGeoTagHeritageEntityDTO> createVideoGeoTagHeritageEntity(@Valid @RequestBody VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to save VideoGeoTagHeritageEntity : {}", videoGeoTagHeritageEntityDTO);
        if (videoGeoTagHeritageEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("videoGeoTagHeritageEntity", "idexists", "A new videoGeoTagHeritageEntity cannot already have an ID")).body(null);
        }
        byte[] arry = new byte[0];
        videoGeoTagHeritageEntityDTO.setVideoFile(arry);
        VideoGeoTagHeritageEntityDTO result = videoGeoTagHeritageEntityService.save(videoGeoTagHeritageEntityDTO);
        return ResponseEntity.created(new URI("/api/videoGeoTagHeritageEntitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("videoGeoTagHeritageEntity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videoGeoTagHeritageEntitys -> Updates an existing videoGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/videoGeoTagHeritageEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VideoGeoTagHeritageEntityDTO> updateVideoGeoTagHeritageEntity(@Valid @RequestBody VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to update VideoGeoTagHeritageEntity : {}", videoGeoTagHeritageEntityDTO);
        if (videoGeoTagHeritageEntityDTO.getId() == null) {
            return createVideoGeoTagHeritageEntity(videoGeoTagHeritageEntityDTO);
        }
        VideoGeoTagHeritageEntityDTO result = videoGeoTagHeritageEntityService.save(videoGeoTagHeritageEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("videoGeoTagHeritageEntity", videoGeoTagHeritageEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videoGeoTagHeritageEntitys -> get all the videoGeoTagHeritageEntitys.
     */
    @RequestMapping(value = "/videoGeoTagHeritageEntitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<VideoGeoTagHeritageEntityDTO>> getAllVideoGeoTagHeritageEntitys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VideoGeoTagHeritageEntitys");
        Page<VideoGeoTagHeritageEntity> page = videoGeoTagHeritageEntityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/videoGeoTagHeritageEntitys");
        return new ResponseEntity<>(page.getContent().stream()
            .map(videoGeoTagHeritageEntityMapper::videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /videoGeoTagHeritageEntitys/:id -> get the "id" videoGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/videoGeoTagHeritageEntitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VideoGeoTagHeritageEntityDTO> getVideoGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to get VideoGeoTagHeritageEntity : {}", id);
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityService.findOne(id);
        return Optional.ofNullable(videoGeoTagHeritageEntityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /videoGeoTagHeritageEntitys/:id -> delete the "id" videoGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/videoGeoTagHeritageEntitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVideoGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to delete VideoGeoTagHeritageEntity : {}", id);
        videoGeoTagHeritageEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("videoGeoTagHeritageEntity", id.toString())).build();
    }
    
    @RequestMapping(value = "/videoGeoTagHeritageEntitysGeoJson",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        @Transactional(readOnly = true)
        public  FeatureCollection  getAllVideoGeoTagHeritageEntitysAsGeoJSON( )
            throws URISyntaxException {
        	
         
            log.debug("REST request to get a page of videoGeoTagHeritageEntityService All as list");
            List<VideoGeoTagHeritageEntity> page = videoGeoTagHeritageEntityService.findAllAsAList();
            
            /*
             * We create a FeatureCollection into which we will put each Feature  with Geometry Objects
             */
            FeatureCollection collection = new FeatureCollection();
            
     
            for(VideoGeoTagHeritageEntity item: page)
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

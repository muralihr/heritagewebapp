package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.ImageGeoTagHeritageEntityMapper;
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

/**
 * REST controller for managing ImageGeoTagHeritageEntity.
 */
@RestController
@RequestMapping("/api")
public class ImageGeoTagHeritageEntityResource {

    private final Logger log = LoggerFactory.getLogger(ImageGeoTagHeritageEntityResource.class);
        
    @Inject
    private ImageGeoTagHeritageEntityService imageGeoTagHeritageEntityService;
    
    @Inject
    private ImageGeoTagHeritageEntityMapper imageGeoTagHeritageEntityMapper;
    
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
}

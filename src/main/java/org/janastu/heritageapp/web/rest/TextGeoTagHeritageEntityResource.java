package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.service.TextGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.TextGeoTagHeritageEntityMapper;
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
 * REST controller for managing TextGeoTagHeritageEntity.
 */
@RestController
@RequestMapping("/api")
public class TextGeoTagHeritageEntityResource {

    private final Logger log = LoggerFactory.getLogger(TextGeoTagHeritageEntityResource.class);
        
    @Inject
    private TextGeoTagHeritageEntityService textGeoTagHeritageEntityService;
    
    @Inject
    private TextGeoTagHeritageEntityMapper textGeoTagHeritageEntityMapper;
    
    /**
     * POST  /textGeoTagHeritageEntitys -> Create a new textGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/textGeoTagHeritageEntitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TextGeoTagHeritageEntityDTO> createTextGeoTagHeritageEntity(@Valid @RequestBody TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to save TextGeoTagHeritageEntity : {}", textGeoTagHeritageEntityDTO);
        if (textGeoTagHeritageEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("textGeoTagHeritageEntity", "idexists", "A new textGeoTagHeritageEntity cannot already have an ID")).body(null);
        }
        TextGeoTagHeritageEntityDTO result = textGeoTagHeritageEntityService.save(textGeoTagHeritageEntityDTO);
        return ResponseEntity.created(new URI("/api/textGeoTagHeritageEntitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("textGeoTagHeritageEntity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /textGeoTagHeritageEntitys -> Updates an existing textGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/textGeoTagHeritageEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TextGeoTagHeritageEntityDTO> updateTextGeoTagHeritageEntity(@Valid @RequestBody TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO) throws URISyntaxException {
        log.debug("REST request to update TextGeoTagHeritageEntity : {}", textGeoTagHeritageEntityDTO);
        if (textGeoTagHeritageEntityDTO.getId() == null) {
            return createTextGeoTagHeritageEntity(textGeoTagHeritageEntityDTO);
        }
        TextGeoTagHeritageEntityDTO result = textGeoTagHeritageEntityService.save(textGeoTagHeritageEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("textGeoTagHeritageEntity", textGeoTagHeritageEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /textGeoTagHeritageEntitys -> get all the textGeoTagHeritageEntitys.
     */
    @RequestMapping(value = "/textGeoTagHeritageEntitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TextGeoTagHeritageEntityDTO>> getAllTextGeoTagHeritageEntitys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TextGeoTagHeritageEntitys");
        Page<TextGeoTagHeritageEntity> page = textGeoTagHeritageEntityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/textGeoTagHeritageEntitys");
        return new ResponseEntity<>(page.getContent().stream()
            .map(textGeoTagHeritageEntityMapper::textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /textGeoTagHeritageEntitys/:id -> get the "id" textGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/textGeoTagHeritageEntitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TextGeoTagHeritageEntityDTO> getTextGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to get TextGeoTagHeritageEntity : {}", id);
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityService.findOne(id);
        return Optional.ofNullable(textGeoTagHeritageEntityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /textGeoTagHeritageEntitys/:id -> delete the "id" textGeoTagHeritageEntity.
     */
    @RequestMapping(value = "/textGeoTagHeritageEntitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTextGeoTagHeritageEntity(@PathVariable Long id) {
        log.debug("REST request to delete TextGeoTagHeritageEntity : {}", id);
        textGeoTagHeritageEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("textGeoTagHeritageEntity", id.toString())).build();
    }
}

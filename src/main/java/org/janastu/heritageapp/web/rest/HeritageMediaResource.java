package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageMedia;
import org.janastu.heritageapp.service.HeritageMediaService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageMediaMapper;
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
 * REST controller for managing HeritageMedia.
 */
@RestController
@RequestMapping("/api")
public class HeritageMediaResource {

    private final Logger log = LoggerFactory.getLogger(HeritageMediaResource.class);
        
    @Inject
    private HeritageMediaService heritageMediaService;
    
    @Inject
    private HeritageMediaMapper heritageMediaMapper;
    
    /**
     * POST  /heritageMedias -> Create a new heritageMedia.
     */
    @RequestMapping(value = "/heritageMedias",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageMediaDTO> createHeritageMedia(@Valid @RequestBody HeritageMediaDTO heritageMediaDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageMedia : {}", heritageMediaDTO);
        if (heritageMediaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageMedia", "idexists", "A new heritageMedia cannot already have an ID")).body(null);
        }
        HeritageMediaDTO result = heritageMediaService.save(heritageMediaDTO);
        return ResponseEntity.created(new URI("/api/heritageMedias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageMedia", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageMedias -> Updates an existing heritageMedia.
     */
    @RequestMapping(value = "/heritageMedias",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageMediaDTO> updateHeritageMedia(@Valid @RequestBody HeritageMediaDTO heritageMediaDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageMedia : {}", heritageMediaDTO);
        if (heritageMediaDTO.getId() == null) {
            return createHeritageMedia(heritageMediaDTO);
        }
        HeritageMediaDTO result = heritageMediaService.save(heritageMediaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageMedia", heritageMediaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageMedias -> get all the heritageMedias.
     */
    @RequestMapping(value = "/heritageMedias",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageMediaDTO>> getAllHeritageMedias(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageMedias");
        Page<HeritageMedia> page = heritageMediaService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageMedias");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageMediaMapper::heritageMediaToHeritageMediaDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageMedias/:id -> get the "id" heritageMedia.
     */
    @RequestMapping(value = "/heritageMedias/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageMediaDTO> getHeritageMedia(@PathVariable Long id) {
        log.debug("REST request to get HeritageMedia : {}", id);
        HeritageMediaDTO heritageMediaDTO = heritageMediaService.findOne(id);
        return Optional.ofNullable(heritageMediaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageMedias/:id -> delete the "id" heritageMedia.
     */
    @RequestMapping(value = "/heritageMedias/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageMedia(@PathVariable Long id) {
        log.debug("REST request to delete HeritageMedia : {}", id);
        heritageMediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageMedia", id.toString())).build();
    }
}

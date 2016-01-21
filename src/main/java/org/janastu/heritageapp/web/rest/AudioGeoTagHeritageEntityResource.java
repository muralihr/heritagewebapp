package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.AudioGeoTagHeritageEntityMapper;
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
        AudioGeoTagHeritageEntityDTO result = audioGeoTagHeritageEntityService.save(audioGeoTagHeritageEntityDTO);
        return ResponseEntity.created(new URI("/api/audioGeoTagHeritageEntitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("audioGeoTagHeritageEntity", result.getId().toString()))
            .body(result);
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
}

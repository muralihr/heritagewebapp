package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageSlide;
import org.janastu.heritageapp.service.HeritageSlideService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageSlideDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageSlideMapper;
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
 * REST controller for managing HeritageSlide.
 */
@RestController
@RequestMapping("/api")
public class HeritageSlideResource {

    private final Logger log = LoggerFactory.getLogger(HeritageSlideResource.class);
        
    @Inject
    private HeritageSlideService heritageSlideService;
    
    @Inject
    private HeritageSlideMapper heritageSlideMapper;
    
    /**
     * POST  /heritageSlides -> Create a new heritageSlide.
     */
    @RequestMapping(value = "/heritageSlides",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageSlideDTO> createHeritageSlide(@Valid @RequestBody HeritageSlideDTO heritageSlideDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageSlide : {}", heritageSlideDTO);
        if (heritageSlideDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageSlide", "idexists", "A new heritageSlide cannot already have an ID")).body(null);
        }
        HeritageSlideDTO result = heritageSlideService.save(heritageSlideDTO);
        return ResponseEntity.created(new URI("/api/heritageSlides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageSlide", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageSlides -> Updates an existing heritageSlide.
     */
    @RequestMapping(value = "/heritageSlides",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageSlideDTO> updateHeritageSlide(@Valid @RequestBody HeritageSlideDTO heritageSlideDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageSlide : {}", heritageSlideDTO);
        if (heritageSlideDTO.getId() == null) {
            return createHeritageSlide(heritageSlideDTO);
        }
        HeritageSlideDTO result = heritageSlideService.save(heritageSlideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageSlide", heritageSlideDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageSlides -> get all the heritageSlides.
     */
    @RequestMapping(value = "/heritageSlides",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageSlideDTO>> getAllHeritageSlides(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageSlides");
        Page<HeritageSlide> page = heritageSlideService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageSlides");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageSlideMapper::heritageSlideToHeritageSlideDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageSlides/:id -> get the "id" heritageSlide.
     */
    @RequestMapping(value = "/heritageSlides/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageSlideDTO> getHeritageSlide(@PathVariable Long id) {
        log.debug("REST request to get HeritageSlide : {}", id);
        HeritageSlideDTO heritageSlideDTO = heritageSlideService.findOne(id);
        return Optional.ofNullable(heritageSlideDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageSlides/:id -> delete the "id" heritageSlide.
     */
    @RequestMapping(value = "/heritageSlides/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageSlide(@PathVariable Long id) {
        log.debug("REST request to delete HeritageSlide : {}", id);
        heritageSlideService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageSlide", id.toString())).build();
    }
}

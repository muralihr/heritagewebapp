package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageWalk;
import org.janastu.heritageapp.service.HeritageWalkService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageWalkDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageWalkMapper;
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
 * REST controller for managing HeritageWalk.
 */
@RestController
@RequestMapping("/api")
public class HeritageWalkResource {

    private final Logger log = LoggerFactory.getLogger(HeritageWalkResource.class);
        
    @Inject
    private HeritageWalkService heritageWalkService;
    
    @Inject
    private HeritageWalkMapper heritageWalkMapper;
    
    /**
     * POST  /heritageWalks -> Create a new heritageWalk.
     */
    @RequestMapping(value = "/heritageWalks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageWalkDTO> createHeritageWalk(@Valid @RequestBody HeritageWalkDTO heritageWalkDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageWalk : {}", heritageWalkDTO);
        if (heritageWalkDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageWalk", "idexists", "A new heritageWalk cannot already have an ID")).body(null);
        }
        HeritageWalkDTO result = heritageWalkService.save(heritageWalkDTO);
        return ResponseEntity.created(new URI("/api/heritageWalks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageWalk", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageWalks -> Updates an existing heritageWalk.
     */
    @RequestMapping(value = "/heritageWalks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageWalkDTO> updateHeritageWalk(@Valid @RequestBody HeritageWalkDTO heritageWalkDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageWalk : {}", heritageWalkDTO);
        if (heritageWalkDTO.getId() == null) {
            return createHeritageWalk(heritageWalkDTO);
        }
        HeritageWalkDTO result = heritageWalkService.save(heritageWalkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageWalk", heritageWalkDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageWalks -> get all the heritageWalks.
     */
    @RequestMapping(value = "/heritageWalks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageWalkDTO>> getAllHeritageWalks(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageWalks");
        Page<HeritageWalk> page = heritageWalkService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageWalks");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageWalkMapper::heritageWalkToHeritageWalkDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageWalks/:id -> get the "id" heritageWalk.
     */
    @RequestMapping(value = "/heritageWalks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageWalkDTO> getHeritageWalk(@PathVariable Long id) {
        log.debug("REST request to get HeritageWalk : {}", id);
        HeritageWalkDTO heritageWalkDTO = heritageWalkService.findOne(id);
        return Optional.ofNullable(heritageWalkDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageWalks/:id -> delete the "id" heritageWalk.
     */
    @RequestMapping(value = "/heritageWalks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageWalk(@PathVariable Long id) {
        log.debug("REST request to delete HeritageWalk : {}", id);
        heritageWalkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageWalk", id.toString())).build();
    }
}

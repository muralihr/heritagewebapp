package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageApp;
import org.janastu.heritageapp.service.HeritageAppService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageAppDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageAppMapper;
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
 * REST controller for managing HeritageApp.
 */
@RestController
@RequestMapping("/api")
public class HeritageAppResource {

    private final Logger log = LoggerFactory.getLogger(HeritageAppResource.class);
        
    @Inject
    private HeritageAppService heritageAppService;
    
    @Inject
    private HeritageAppMapper heritageAppMapper;
    
    /**
     * POST  /heritageApps -> Create a new heritageApp.
     */
    @RequestMapping(value = "/heritageApps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageAppDTO> createHeritageApp(@Valid @RequestBody HeritageAppDTO heritageAppDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageApp : {}", heritageAppDTO);
        if (heritageAppDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageApp", "idexists", "A new heritageApp cannot already have an ID")).body(null);
        }
        HeritageAppDTO result = heritageAppService.save(heritageAppDTO);
        return ResponseEntity.created(new URI("/api/heritageApps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageApp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageApps -> Updates an existing heritageApp.
     */
    @RequestMapping(value = "/heritageApps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageAppDTO> updateHeritageApp(@Valid @RequestBody HeritageAppDTO heritageAppDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageApp : {}", heritageAppDTO);
        if (heritageAppDTO.getId() == null) {
            return createHeritageApp(heritageAppDTO);
        }
        HeritageAppDTO result = heritageAppService.save(heritageAppDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageApp", heritageAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageApps -> get all the heritageApps.
     */
    @RequestMapping(value = "/heritageApps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageAppDTO>> getAllHeritageApps(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageApps");
        Page<HeritageApp> page = heritageAppService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageApps");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageAppMapper::heritageAppToHeritageAppDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageApps/:id -> get the "id" heritageApp.
     */
    @RequestMapping(value = "/heritageApps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageAppDTO> getHeritageApp(@PathVariable Long id) {
        log.debug("REST request to get HeritageApp : {}", id);
        HeritageAppDTO heritageAppDTO = heritageAppService.findOne(id);
        return Optional.ofNullable(heritageAppDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageApps/:id -> delete the "id" heritageApp.
     */
    @RequestMapping(value = "/heritageApps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageApp(@PathVariable Long id) {
        log.debug("REST request to delete HeritageApp : {}", id);
        heritageAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageApp", id.toString())).build();
    }
}

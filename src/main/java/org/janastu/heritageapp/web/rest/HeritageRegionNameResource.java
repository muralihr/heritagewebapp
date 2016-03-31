package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageRegionName;
import org.janastu.heritageapp.service.HeritageRegionNameService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageRegionNameDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageRegionNameMapper;
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
 * REST controller for managing HeritageRegionName.
 */
@RestController
@RequestMapping("/api")
public class HeritageRegionNameResource {

    private final Logger log = LoggerFactory.getLogger(HeritageRegionNameResource.class);
        
    @Inject
    private HeritageRegionNameService heritageRegionNameService;
    
    @Inject
    private HeritageRegionNameMapper heritageRegionNameMapper;
    
    /**
     * POST  /heritageRegionNames -> Create a new heritageRegionName.
     */
    @RequestMapping(value = "/heritageRegionNames",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageRegionNameDTO> createHeritageRegionName(@Valid @RequestBody HeritageRegionNameDTO heritageRegionNameDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageRegionName : {}", heritageRegionNameDTO);
        if (heritageRegionNameDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageRegionName", "idexists", "A new heritageRegionName cannot already have an ID")).body(null);
        }
        HeritageRegionNameDTO result = heritageRegionNameService.save(heritageRegionNameDTO);
        return ResponseEntity.created(new URI("/api/heritageRegionNames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageRegionName", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageRegionNames -> Updates an existing heritageRegionName.
     */
    @RequestMapping(value = "/heritageRegionNames",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageRegionNameDTO> updateHeritageRegionName(@Valid @RequestBody HeritageRegionNameDTO heritageRegionNameDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageRegionName : {}", heritageRegionNameDTO);
        if (heritageRegionNameDTO.getId() == null) {
            return createHeritageRegionName(heritageRegionNameDTO);
        }
        HeritageRegionNameDTO result = heritageRegionNameService.save(heritageRegionNameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageRegionName", heritageRegionNameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageRegionNames -> get all the heritageRegionNames.
     */
    @RequestMapping(value = "/heritageRegionNames",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageRegionNameDTO>> getAllHeritageRegionNames(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageRegionNames");
        Page<HeritageRegionName> page = heritageRegionNameService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageRegionNames");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageRegionNameMapper::heritageRegionNameToHeritageRegionNameDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageRegionNames/:id -> get the "id" heritageRegionName.
     */
    @RequestMapping(value = "/heritageRegionNames/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageRegionNameDTO> getHeritageRegionName(@PathVariable Long id) {
        log.debug("REST request to get HeritageRegionName : {}", id);
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameService.findOne(id);
        return Optional.ofNullable(heritageRegionNameDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageRegionNames/:id -> delete the "id" heritageRegionName.
     */
    @RequestMapping(value = "/heritageRegionNames/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageRegionName(@PathVariable Long id) {
        log.debug("REST request to delete HeritageRegionName : {}", id);
        heritageRegionNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageRegionName", id.toString())).build();
    }
}

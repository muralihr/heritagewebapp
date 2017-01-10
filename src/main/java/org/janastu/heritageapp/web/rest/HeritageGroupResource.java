package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.service.HeritageGroupService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupMapper;
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
 * REST controller for managing HeritageGroup.
 */
@RestController
@RequestMapping("/api")
public class HeritageGroupResource {

    private final Logger log = LoggerFactory.getLogger(HeritageGroupResource.class);
        
    @Inject
    private HeritageGroupService heritageGroupService;
    
    @Inject
    private HeritageGroupMapper heritageGroupMapper;
    
    /**
     * POST  /heritageGroups -> Create a new heritageGroup.
     */
    @RequestMapping(value = "/heritageGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupDTO> createHeritageGroup(@Valid @RequestBody HeritageGroupDTO heritageGroupDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageGroup : {}", heritageGroupDTO);
        if (heritageGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageGroup", "idexists", "A new heritageGroup cannot already have an ID")).body(null);
        }
        HeritageGroupDTO result = heritageGroupService.save(heritageGroupDTO);
        return ResponseEntity.created(new URI("/api/heritageGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageGroups -> Updates an existing heritageGroup.
     */
    @RequestMapping(value = "/heritageGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupDTO> updateHeritageGroup(@Valid @RequestBody HeritageGroupDTO heritageGroupDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageGroup : {}", heritageGroupDTO);
        if (heritageGroupDTO.getId() == null) {
            return createHeritageGroup(heritageGroupDTO);
        }
        HeritageGroupDTO result = heritageGroupService.save(heritageGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageGroup", heritageGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageGroups -> get all the heritageGroups.
     */
    @RequestMapping(value = "/heritageGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageGroupDTO>> getAllHeritageGroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageGroups");
        Page<HeritageGroup> page = heritageGroupService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageGroups");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageGroupMapper::heritageGroupToHeritageGroupDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/heritageGroups2",
            method = {RequestMethod.POST, RequestMethod.GET},
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public ResponseEntity<List<HeritageGroup>> getAllHeritageGroups2( )
            throws URISyntaxException {
            log.debug("REST request to get a page of HeritageGroups");
            List<HeritageGroup> groups =   heritageGroupService.findAll( ); 
            HttpHeaders headers = new HttpHeaders();
             //= PaginationUtil/(  "/api/heritageCategorys");
            return new ResponseEntity<>(groups, headers, HttpStatus.OK);
        }


    /**
     * GET  /heritageGroups/:id -> get the "id" heritageGroup.
     */
    @RequestMapping(value = "/heritageGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupDTO> getHeritageGroup(@PathVariable Long id) {
        log.debug("REST request to get HeritageGroup : {}", id);
        HeritageGroupDTO heritageGroupDTO = heritageGroupService.findOne(id);
        return Optional.ofNullable(heritageGroupDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageGroups/:id -> delete the "id" heritageGroup.
     */
    @RequestMapping(value = "/heritageGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageGroup(@PathVariable Long id) {
        log.debug("REST request to delete HeritageGroup : {}", id);
        heritageGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageGroup", id.toString())).build();
    }
}

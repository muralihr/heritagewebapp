package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageGroupUser;
import org.janastu.heritageapp.service.HeritageGroupUserService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupUserDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupUserMapper;
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
 * REST controller for managing HeritageGroupUser.
 */
@RestController
@RequestMapping("/api")
public class HeritageGroupUserResource {

    private final Logger log = LoggerFactory.getLogger(HeritageGroupUserResource.class);
        
    @Inject
    private HeritageGroupUserService heritageGroupUserService;
    
    @Inject
    private HeritageGroupUserMapper heritageGroupUserMapper;
    
    /**
     * POST  /heritageGroupUsers -> Create a new heritageGroupUser.
     */
    @RequestMapping(value = "/heritageGroupUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupUserDTO> createHeritageGroupUser(@Valid @RequestBody HeritageGroupUserDTO heritageGroupUserDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageGroupUser : {}", heritageGroupUserDTO);
        if (heritageGroupUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageGroupUser", "idexists", "A new heritageGroupUser cannot already have an ID")).body(null);
        }
        HeritageGroupUserDTO result = heritageGroupUserService.save(heritageGroupUserDTO);
        return ResponseEntity.created(new URI("/api/heritageGroupUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageGroupUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageGroupUsers -> Updates an existing heritageGroupUser.
     */
    @RequestMapping(value = "/heritageGroupUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupUserDTO> updateHeritageGroupUser(@Valid @RequestBody HeritageGroupUserDTO heritageGroupUserDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageGroupUser : {}", heritageGroupUserDTO);
        if (heritageGroupUserDTO.getId() == null) {
            return createHeritageGroupUser(heritageGroupUserDTO);
        }
        HeritageGroupUserDTO result = heritageGroupUserService.save(heritageGroupUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageGroupUser", heritageGroupUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageGroupUsers -> get all the heritageGroupUsers.
     */
    @RequestMapping(value = "/heritageGroupUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageGroupUserDTO>> getAllHeritageGroupUsers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageGroupUsers");
        Page<HeritageGroupUser> page = heritageGroupUserService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageGroupUsers");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageGroupUserMapper::heritageGroupUserToHeritageGroupUserDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageGroupUsers/:id -> get the "id" heritageGroupUser.
     */
    @RequestMapping(value = "/heritageGroupUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupUserDTO> getHeritageGroupUser(@PathVariable Long id) {
        log.debug("REST request to get HeritageGroupUser : {}", id);
        HeritageGroupUserDTO heritageGroupUserDTO = heritageGroupUserService.findOne(id);
        return Optional.ofNullable(heritageGroupUserDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageGroupUsers/:id -> delete the "id" heritageGroupUser.
     */
    @RequestMapping(value = "/heritageGroupUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageGroupUser(@PathVariable Long id) {
        log.debug("REST request to delete HeritageGroupUser : {}", id);
        heritageGroupUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageGroupUser", id.toString())).build();
    }
}

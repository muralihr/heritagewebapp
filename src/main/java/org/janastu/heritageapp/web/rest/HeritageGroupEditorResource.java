package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageGroupEditor;
import org.janastu.heritageapp.service.HeritageGroupEditorService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupEditorDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupEditorMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing HeritageGroupEditor.
 */
@RestController
@RequestMapping("/api")
public class HeritageGroupEditorResource {

    private final Logger log = LoggerFactory.getLogger(HeritageGroupEditorResource.class);
        
    @Inject
    private HeritageGroupEditorService heritageGroupEditorService;
    
    @Inject
    private HeritageGroupEditorMapper heritageGroupEditorMapper;
    
    /**
     * POST  /heritageGroupEditors -> Create a new heritageGroupEditor.
     */
    @RequestMapping(value = "/heritageGroupEditors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupEditorDTO> createHeritageGroupEditor(@RequestBody HeritageGroupEditorDTO heritageGroupEditorDTO) throws URISyntaxException {
        log.debug("REST request to save HeritageGroupEditor : {}", heritageGroupEditorDTO);
        if (heritageGroupEditorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageGroupEditor", "idexists", "A new heritageGroupEditor cannot already have an ID")).body(null);
        }
        HeritageGroupEditorDTO result = heritageGroupEditorService.save(heritageGroupEditorDTO);
        return ResponseEntity.created(new URI("/api/heritageGroupEditors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageGroupEditor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageGroupEditors -> Updates an existing heritageGroupEditor.
     */
    @RequestMapping(value = "/heritageGroupEditors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupEditorDTO> updateHeritageGroupEditor(@RequestBody HeritageGroupEditorDTO heritageGroupEditorDTO) throws URISyntaxException {
        log.debug("REST request to update HeritageGroupEditor : {}", heritageGroupEditorDTO);
        if (heritageGroupEditorDTO.getId() == null) {
            return createHeritageGroupEditor(heritageGroupEditorDTO);
        }
        HeritageGroupEditorDTO result = heritageGroupEditorService.save(heritageGroupEditorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageGroupEditor", heritageGroupEditorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageGroupEditors -> get all the heritageGroupEditors.
     */
    @RequestMapping(value = "/heritageGroupEditors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<HeritageGroupEditorDTO>> getAllHeritageGroupEditors(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageGroupEditors");
        Page<HeritageGroupEditor> page = heritageGroupEditorService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageGroupEditors");
        return new ResponseEntity<>(page.getContent().stream()
            .map(heritageGroupEditorMapper::heritageGroupEditorToHeritageGroupEditorDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageGroupEditors/:id -> get the "id" heritageGroupEditor.
     */
    @RequestMapping(value = "/heritageGroupEditors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageGroupEditorDTO> getHeritageGroupEditor(@PathVariable Long id) {
        log.debug("REST request to get HeritageGroupEditor : {}", id);
        HeritageGroupEditorDTO heritageGroupEditorDTO = heritageGroupEditorService.findOne(id);
        return Optional.ofNullable(heritageGroupEditorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageGroupEditors/:id -> delete the "id" heritageGroupEditor.
     */
    @RequestMapping(value = "/heritageGroupEditors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageGroupEditor(@PathVariable Long id) {
        log.debug("REST request to delete HeritageGroupEditor : {}", id);
        heritageGroupEditorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageGroupEditor", id.toString())).build();
    }
}

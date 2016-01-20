package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.repository.HeritageCategoryRepository;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HeritageCategory.
 */
@RestController
@RequestMapping("/api")
public class HeritageCategoryResource {

    private final Logger log = LoggerFactory.getLogger(HeritageCategoryResource.class);
        
    @Inject
    private HeritageCategoryRepository heritageCategoryRepository;
    
    /**
     * POST  /heritageCategorys -> Create a new heritageCategory.
     */
    @RequestMapping(value = "/heritageCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageCategory> createHeritageCategory(@Valid @RequestBody HeritageCategory heritageCategory) throws URISyntaxException {
        log.debug("REST request to save HeritageCategory : {}", heritageCategory);
        if (heritageCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageCategory", "idexists", "A new heritageCategory cannot already have an ID")).body(null);
        }
        HeritageCategory result = heritageCategoryRepository.save(heritageCategory);
        return ResponseEntity.created(new URI("/api/heritageCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageCategorys -> Updates an existing heritageCategory.
     */
    @RequestMapping(value = "/heritageCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageCategory> updateHeritageCategory(@Valid @RequestBody HeritageCategory heritageCategory) throws URISyntaxException {
        log.debug("REST request to update HeritageCategory : {}", heritageCategory);
        if (heritageCategory.getId() == null) {
            return createHeritageCategory(heritageCategory);
        }
        HeritageCategory result = heritageCategoryRepository.save(heritageCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageCategory", heritageCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageCategorys -> get all the heritageCategorys.
     */
    @RequestMapping(value = "/heritageCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HeritageCategory>> getAllHeritageCategorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageCategorys");
        Page<HeritageCategory> page = heritageCategoryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageCategorys/:id -> get the "id" heritageCategory.
     */
    @RequestMapping(value = "/heritageCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageCategory> getHeritageCategory(@PathVariable Long id) {
        log.debug("REST request to get HeritageCategory : {}", id);
        HeritageCategory heritageCategory = heritageCategoryRepository.findOne(id);
        return Optional.ofNullable(heritageCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageCategorys/:id -> delete the "id" heritageCategory.
     */
    @RequestMapping(value = "/heritageCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageCategory(@PathVariable Long id) {
        log.debug("REST request to delete HeritageCategory : {}", id);
        heritageCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageCategory", id.toString())).build();
    }
}

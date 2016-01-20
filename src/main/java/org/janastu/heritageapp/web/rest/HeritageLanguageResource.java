package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.repository.HeritageLanguageRepository;
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
 * REST controller for managing HeritageLanguage.
 */
@RestController
@RequestMapping("/api")
public class HeritageLanguageResource {

    private final Logger log = LoggerFactory.getLogger(HeritageLanguageResource.class);
        
    @Inject
    private HeritageLanguageRepository heritageLanguageRepository;
    
    /**
     * POST  /heritageLanguages -> Create a new heritageLanguage.
     */
    @RequestMapping(value = "/heritageLanguages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageLanguage> createHeritageLanguage(@Valid @RequestBody HeritageLanguage heritageLanguage) throws URISyntaxException {
        log.debug("REST request to save HeritageLanguage : {}", heritageLanguage);
        if (heritageLanguage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heritageLanguage", "idexists", "A new heritageLanguage cannot already have an ID")).body(null);
        }
        HeritageLanguage result = heritageLanguageRepository.save(heritageLanguage);
        return ResponseEntity.created(new URI("/api/heritageLanguages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heritageLanguage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heritageLanguages -> Updates an existing heritageLanguage.
     */
    @RequestMapping(value = "/heritageLanguages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageLanguage> updateHeritageLanguage(@Valid @RequestBody HeritageLanguage heritageLanguage) throws URISyntaxException {
        log.debug("REST request to update HeritageLanguage : {}", heritageLanguage);
        if (heritageLanguage.getId() == null) {
            return createHeritageLanguage(heritageLanguage);
        }
        HeritageLanguage result = heritageLanguageRepository.save(heritageLanguage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heritageLanguage", heritageLanguage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heritageLanguages -> get all the heritageLanguages.
     */
    @RequestMapping(value = "/heritageLanguages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HeritageLanguage>> getAllHeritageLanguages(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HeritageLanguages");
        Page<HeritageLanguage> page = heritageLanguageRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/heritageLanguages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /heritageLanguages/:id -> get the "id" heritageLanguage.
     */
    @RequestMapping(value = "/heritageLanguages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeritageLanguage> getHeritageLanguage(@PathVariable Long id) {
        log.debug("REST request to get HeritageLanguage : {}", id);
        HeritageLanguage heritageLanguage = heritageLanguageRepository.findOne(id);
        return Optional.ofNullable(heritageLanguage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heritageLanguages/:id -> delete the "id" heritageLanguage.
     */
    @RequestMapping(value = "/heritageLanguages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeritageLanguage(@PathVariable Long id) {
        log.debug("REST request to delete HeritageLanguage : {}", id);
        heritageLanguageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heritageLanguage", id.toString())).build();
    }
}

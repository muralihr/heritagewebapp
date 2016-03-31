package org.janastu.heritageapp.service;

import org.janastu.heritageapp.domain.HeritageGroupEditor;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupEditorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing HeritageGroupEditor.
 */
public interface HeritageGroupEditorService {

    /**
     * Save a heritageGroupEditor.
     * @return the persisted entity
     */
    public HeritageGroupEditorDTO save(HeritageGroupEditorDTO heritageGroupEditorDTO);

    /**
     *  get all the heritageGroupEditors.
     *  @return the list of entities
     */
    public Page<HeritageGroupEditor> findAll(Pageable pageable);

    /**
     *  get the "id" heritageGroupEditor.
     *  @return the entity
     */
    public HeritageGroupEditorDTO findOne(Long id);

    /**
     *  delete the "id" heritageGroupEditor.
     */
    public void delete(Long id);
}

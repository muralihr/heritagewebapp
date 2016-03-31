package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageGroupEditor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageGroupEditor entity.
 */
public interface HeritageGroupEditorRepository extends JpaRepository<HeritageGroupEditor,Long> {

    @Query("select heritageGroupEditor from HeritageGroupEditor heritageGroupEditor where heritageGroupEditor.editor.login = ?#{principal.username}")
    List<HeritageGroupEditor> findByEditorIsCurrentUser();

}

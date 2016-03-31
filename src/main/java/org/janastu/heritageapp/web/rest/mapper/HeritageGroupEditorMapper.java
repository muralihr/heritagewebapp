package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupEditorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageGroupEditor and its DTO HeritageGroupEditorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageGroupEditorMapper {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "editor.id", target = "editorId")
    @Mapping(source = "editor.login", target = "editorLogin")
    HeritageGroupEditorDTO heritageGroupEditorToHeritageGroupEditorDTO(HeritageGroupEditor heritageGroupEditor);

    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "editorId", target = "editor")
    HeritageGroupEditor heritageGroupEditorDTOToHeritageGroupEditor(HeritageGroupEditorDTO heritageGroupEditorDTO);

    default HeritageGroup heritageGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageGroup heritageGroup = new HeritageGroup();
        heritageGroup.setId(id);
        return heritageGroup;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}

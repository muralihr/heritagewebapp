package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageGroup and its DTO HeritageGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageGroupMapper {

    HeritageGroupDTO heritageGroupToHeritageGroupDTO(HeritageGroup heritageGroup);

    HeritageGroup heritageGroupDTOToHeritageGroup(HeritageGroupDTO heritageGroupDTO);
}

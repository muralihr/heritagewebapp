package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageRegionNameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageRegionName and its DTO HeritageRegionNameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageRegionNameMapper {

    HeritageRegionNameDTO heritageRegionNameToHeritageRegionNameDTO(HeritageRegionName heritageRegionName);

    HeritageRegionName heritageRegionNameDTOToHeritageRegionName(HeritageRegionNameDTO heritageRegionNameDTO);
}

package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TextGeoTagHeritageEntity and its DTO TextGeoTagHeritageEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TextGeoTagHeritageEntityMapper {

    @Mapping(source = "heritageCategory.id", target = "heritageCategoryId")
    @Mapping(source = "heritageCategory.categoryName", target = "heritageCategoryCategoryName")
    @Mapping(source = "heritageLanguage.id", target = "heritageLanguageId")
    @Mapping(source = "heritageLanguage.heritageLanguage", target = "heritageLanguageHeritageLanguage")
    TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(TextGeoTagHeritageEntity textGeoTagHeritageEntity);

    @Mapping(source = "heritageCategoryId", target = "heritageCategory")
    @Mapping(source = "heritageLanguageId", target = "heritageLanguage")
    TextGeoTagHeritageEntity textGeoTagHeritageEntityDTOToTextGeoTagHeritageEntity(TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO);

    default HeritageCategory heritageCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageCategory heritageCategory = new HeritageCategory();
        heritageCategory.setId(id);
        return heritageCategory;
    }

    default HeritageLanguage heritageLanguageFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageLanguage heritageLanguage = new HeritageLanguage();
        heritageLanguage.setId(id);
        return heritageLanguage;
    }
}

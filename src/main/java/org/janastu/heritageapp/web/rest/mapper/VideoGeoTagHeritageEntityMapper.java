package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VideoGeoTagHeritageEntity and its DTO VideoGeoTagHeritageEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VideoGeoTagHeritageEntityMapper {

    @Mapping(source = "heritageCategory.id", target = "heritageCategoryId")
    @Mapping(source = "heritageCategory.categoryName", target = "heritageCategoryCategoryName")
    @Mapping(source = "heritageLanguage.id", target = "heritageLanguageId")
    @Mapping(source = "heritageLanguage.heritageLanguage", target = "heritageLanguageHeritageLanguage")
    VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(VideoGeoTagHeritageEntity videoGeoTagHeritageEntity);

    @Mapping(source = "heritageCategoryId", target = "heritageCategory")
    @Mapping(source = "heritageLanguageId", target = "heritageLanguage")
    VideoGeoTagHeritageEntity videoGeoTagHeritageEntityDTOToVideoGeoTagHeritageEntity(VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO);

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

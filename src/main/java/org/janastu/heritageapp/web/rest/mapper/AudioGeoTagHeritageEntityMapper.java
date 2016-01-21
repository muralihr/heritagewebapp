package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AudioGeoTagHeritageEntity and its DTO AudioGeoTagHeritageEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AudioGeoTagHeritageEntityMapper {

    @Mapping(source = "heritageCategory.id", target = "heritageCategoryId")
    @Mapping(source = "heritageCategory.categoryName", target = "heritageCategoryCategoryName")
    @Mapping(source = "heritageLanguage.id", target = "heritageLanguageId")
    @Mapping(source = "heritageLanguage.heritageLanguage", target = "heritageLanguageHeritageLanguage")
    AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(AudioGeoTagHeritageEntity audioGeoTagHeritageEntity);

    @Mapping(source = "heritageCategoryId", target = "heritageCategory")
    @Mapping(source = "heritageLanguageId", target = "heritageLanguage")
    AudioGeoTagHeritageEntity audioGeoTagHeritageEntityDTOToAudioGeoTagHeritageEntity(AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO);

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

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
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.login", target = "customerLogin")
    TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(TextGeoTagHeritageEntity textGeoTagHeritageEntity);

    @Mapping(source = "heritageCategoryId", target = "heritageCategory")
    @Mapping(source = "heritageLanguageId", target = "heritageLanguage")
    @Mapping(source = "customerId", target = "customer")
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

    default User UserFromId(Long id) {
        if (id == null) {
            return null;
        }
        User User = new User();
        User.setId(id);
        return User;
    }
}

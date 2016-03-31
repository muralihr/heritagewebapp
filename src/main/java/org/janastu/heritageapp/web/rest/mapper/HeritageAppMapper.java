package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageAppDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageApp and its DTO HeritageAppDTO.
 */
@Mapper(componentModel = "spring", uses = {HeritageRegionNameMapper.class, HeritageGroupMapper.class, HeritageLanguage.class, HeritageCategory.class, })
public interface HeritageAppMapper {

    HeritageAppDTO heritageAppToHeritageAppDTO(HeritageApp heritageApp);

    HeritageApp heritageAppDTOToHeritageApp(HeritageAppDTO heritageAppDTO);

    default HeritageRegionName heritageRegionNameFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageRegionName heritageRegionName = new HeritageRegionName();
        heritageRegionName.setId(id);
        return heritageRegionName;
    }

    default HeritageGroup heritageGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageGroup heritageGroup = new HeritageGroup();
        heritageGroup.setId(id);
        return heritageGroup;
    }

    default HeritageLanguage heritageLanguageFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageLanguage heritageLanguage = new HeritageLanguage();
        heritageLanguage.setId(id);
        return heritageLanguage;
    }

    default HeritageCategory heritageCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageCategory heritageCategory = new HeritageCategory();
        heritageCategory.setId(id);
        return heritageCategory;
    }
}

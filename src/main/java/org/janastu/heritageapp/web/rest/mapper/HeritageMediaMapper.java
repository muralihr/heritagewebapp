package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageMedia and its DTO HeritageMediaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageMediaMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryCategoryName")
    @Mapping(source = "language.id", target = "languageId")
    @Mapping(source = "language.heritageLanguage", target = "languageHeritageLanguage")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "heritageApp.id", target = "heritageAppId")
    @Mapping(source = "heritageApp.name", target = "heritageAppName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    HeritageMediaDTO heritageMediaToHeritageMediaDTO(HeritageMedia heritageMedia);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "languageId", target = "language")
    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "heritageAppId", target = "heritageApp")
    @Mapping(source = "userId", target = "user")
    HeritageMedia heritageMediaDTOToHeritageMedia(HeritageMediaDTO heritageMediaDTO);

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

    default HeritageGroup heritageGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageGroup heritageGroup = new HeritageGroup();
        heritageGroup.setId(id);
        return heritageGroup;
    }

    default HeritageApp heritageAppFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageApp heritageApp = new HeritageApp();
        heritageApp.setId(id);
        return heritageApp;
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

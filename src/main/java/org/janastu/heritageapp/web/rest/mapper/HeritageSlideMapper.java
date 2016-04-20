package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageSlideDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageSlide and its DTO HeritageSlideDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageSlideMapper {

    @Mapping(source = "heritageMedia.id", target = "heritageMediaId")
    @Mapping(source = "heritageMedia.title", target = "heritageMediaTitle")
    @Mapping(source = "heritageWalk.id", target = "heritageWalkId")
    @Mapping(source = "heritageWalk.name", target = "heritageWalkName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    HeritageSlideDTO heritageSlideToHeritageSlideDTO(HeritageSlide heritageSlide);

    @Mapping(source = "heritageMediaId", target = "heritageMedia")
    @Mapping(source = "heritageWalkId", target = "heritageWalk")
    @Mapping(source = "userId", target = "user")
    HeritageSlide heritageSlideDTOToHeritageSlide(HeritageSlideDTO heritageSlideDTO);

    default HeritageMedia heritageMediaFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageMedia heritageMedia = new HeritageMedia();
        heritageMedia.setId(id);
        return heritageMedia;
    }

    default HeritageWalk heritageWalkFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageWalk heritageWalk = new HeritageWalk();
        heritageWalk.setId(id);
        return heritageWalk;
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

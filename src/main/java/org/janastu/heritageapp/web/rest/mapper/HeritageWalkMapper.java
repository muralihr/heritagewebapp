package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageWalkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageWalk and its DTO HeritageWalkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageWalkMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    HeritageWalkDTO heritageWalkToHeritageWalkDTO(HeritageWalk heritageWalk);

    @Mapping(source = "userId", target = "user")
    HeritageWalk heritageWalkDTOToHeritageWalk(HeritageWalkDTO heritageWalkDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}

package org.janastu.heritageapp.web.rest.mapper;

import org.janastu.heritageapp.domain.*;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HeritageGroupUser and its DTO HeritageGroupUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeritageGroupUserMapper {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.login", target = "memberLogin")
    HeritageGroupUserDTO heritageGroupUserToHeritageGroupUserDTO(HeritageGroupUser heritageGroupUser);

    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "memberId", target = "member")
    HeritageGroupUser heritageGroupUserDTOToHeritageGroupUser(HeritageGroupUserDTO heritageGroupUserDTO);

    default HeritageGroup heritageGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        HeritageGroup heritageGroup = new HeritageGroup();
        heritageGroup.setId(id);
        return heritageGroup;
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

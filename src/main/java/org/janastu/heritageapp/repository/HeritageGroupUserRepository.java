package org.janastu.heritageapp.repository;

import org.janastu.heritageapp.domain.HeritageGroupUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HeritageGroupUser entity.
 */
public interface HeritageGroupUserRepository extends JpaRepository<HeritageGroupUser,Long> {

    @Query("select heritageGroupUser from HeritageGroupUser heritageGroupUser where heritageGroupUser.member.login = ?#{principal.username}")
    List<HeritageGroupUser> findByMemberIsCurrentUser();

}

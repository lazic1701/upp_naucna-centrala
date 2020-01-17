package org.milan.naucnacentrala.repository;

import org.milan.naucnacentrala.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query("select e from #{#entityName} e join e.authorities a where a.id = :authorityId")
    List<User> findAllByAuthorityId(@Param("authorityId") long authorityId);
}

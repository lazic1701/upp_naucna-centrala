package org.milan.naucnacentrala.repository;

import org.milan.naucnacentrala.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findOneByName(String name);
}

package org.milan.naucnacentrala.repository;

import org.milan.naucnacentrala.model.Clanarina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClanarinaRepository extends JpaRepository<Clanarina, Integer> {

    public List<Clanarina> findAllByUserIdAndCasopisId(int userId, int casopisId);
}

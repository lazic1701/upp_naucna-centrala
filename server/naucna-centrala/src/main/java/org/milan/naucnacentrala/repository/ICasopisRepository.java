package org.milan.naucnacentrala.repository;

import org.milan.naucnacentrala.model.Casopis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICasopisRepository  extends JpaRepository<Casopis, Integer> {

    List<Casopis> findAllByGlavniUrednikId(int glavniUrednikId);

    Optional<Casopis> findBySellerId(int sellerId);
}

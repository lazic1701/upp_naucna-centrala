package org.milan.naucnacentrala.repository;

import org.milan.naucnacentrala.model.Porudzbina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPorudzbinaRepository extends JpaRepository<Porudzbina, Long> {

    List<Porudzbina> findAllByKupacId(int kupacId);
}

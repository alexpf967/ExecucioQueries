package com.example.demo.repositories;

import com.example.demo.classes.Entrada;
import com.example.demo.classes.Tupla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    @Query(value = "SELECT * FROM entrada e WHERE e.indexb_id = ?1 ORDER BY e.id", nativeQuery = true)
    List<Entrada> findByIndexBID(long bloc_id);
}

package com.example.demo.repositories;

import com.example.demo.classes.Taula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaulaRepository extends JpaRepository<Taula, Long> {
    @Query(value = "SELECT * FROM taula t WHERE t.nom_taula = ?1", nativeQuery = true)
    Taula findByNomTaula (String nom_taula);
    @Query(value = "SELECT t.id FROM taula t WHERE t.nom_taula = ?1", nativeQuery = true)
    Long findIDByNomTaula (String nom_taula);
}

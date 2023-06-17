package com.example.demo.repositories;

import com.example.demo.classes.Entrada;
import com.example.demo.classes.IndexB;
import com.example.demo.classes.Taula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexBRepository extends JpaRepository<IndexB, Long> {
    @Query(value = "SELECT nom_indexb FROM indexb ib WHERE ib.taula_id = ?1", nativeQuery = true)
    String findIndexBbyTaulaID(long taula_id);
    @Query(value = "SELECT i.id FROM indexb i WHERE i.nom_indexb = ?1", nativeQuery = true)
    Long findIDByNomIndexB (String nom_indexb);
    @Query(value = "SELECT i.taula_id FROM indexb i WHERE i.id = ?1", nativeQuery = true)
    Long findTaulaIDByIndexBID (long indexb_id);
    @Query(value = "SELECT i.nom_indexb FROM indexb i WHERE i.taula_id = ?1", nativeQuery = true)
    String findNomIndexBByTaulaID (long taula_id);
    @Query(value = "SELECT * FROM indexb ib WHERE ib.nom_indexb = ?1", nativeQuery = true)
    IndexB findByNomIndexB (String nom_indexb);
    @Query(value = "SELECT i.entries_fulla FROM indexb i WHERE i.id = ?1", nativeQuery = true)
    int EntriesFullaIndexHash (long indexhash_id);
    @Query(value = "SELECT i.id FROM indexb i WHERE i.taula_id = ?1", nativeQuery = true)
    Long findIndexBidByTaulaID (long taula_id);

}

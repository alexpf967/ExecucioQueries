package com.example.demo.repositories;

import com.example.demo.classes.IndexHash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexHashRepository extends JpaRepository<IndexHash, Long> {
    @Query(value = "SELECT nom_index_hash FROM index_hash ih WHERE ih.taula_id = ?1", nativeQuery = true)
    String findIndexHashbyTaulaID(long taula_id);
    @Query(value = "SELECT i.id FROM index_hash i WHERE i.nom_index_hash = ?1", nativeQuery = true)
    Long findIDByNomIndexHash (String nom_indexhash);
    @Query(value = "SELECT i.taula_id FROM index_hash i WHERE i.id = ?1", nativeQuery = true)
    Long findTaulaIDByIndexHashID (long indexhash_id);
    @Query(value = "SELECT i.n_buckets FROM index_hash i WHERE i.id = ?1", nativeQuery = true)
    int NbucketsIndexHash (long indexhash_id);
    @Query(value = "SELECT i.nom_index_hash FROM index_hash i WHERE i.taula_id = ?1", nativeQuery = true)
    String findNomIndexHashByTaulaID (long taula_id);
    @Query(value = "SELECT i.id FROM index_hash i WHERE i.taula_id = ?1", nativeQuery = true)
    Long findIndexHashidByTaulaID (long taula_id);
    @Query(value = "SELECT i.entries_bucket FROM index_hash i WHERE i.id = ?1", nativeQuery = true)
    int EntriesBucketIndexHash (long indexhash_id);
}

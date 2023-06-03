package com.example.demo.repositories;

import com.example.demo.classes.Entrada;
import com.example.demo.classes.Tupla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    @Query(value = "SELECT * FROM entrada e WHERE e.indexb_id = ?1 ORDER BY e.tupla_id", nativeQuery = true)
    List<Entrada> findByIndexBID(long indexB_id);
    @Query(value = "SELECT * FROM entrada e WHERE e.index_hash_id = ?1 ORDER BY e.tupla_id", nativeQuery = true)
    List<Entrada> findByIndexHashID(long indexHash_id);
    @Query(value = "SELECT * FROM entrada e WHERE e.indexb_id = ?1 and e.n_fulla = ?2 ORDER BY e.tupla_id", nativeQuery = true)
    List<Entrada> findFullaNIndexB(long indexB_id, int N);

    @Query(value = "SELECT * FROM entrada e WHERE e.index_hash_id = ?1 and e.n_bucket = ?2 ORDER BY e.tupla_id", nativeQuery = true)
    List<Entrada> findBucketNIndexHash(long indexHash_id, int N);

    @Query(value = "SELECT * FROM entrada e WHERE e.tupla_id = ?1", nativeQuery = true)
    Entrada findByTuplaID(long tupla_id);
    @Query(value = "SELECT count(*) FROM entrada e WHERE e.indexb_id = ?1", nativeQuery = true)
    int getEntradaForIndexB(long indexb_id);
    @Query(value = "SELECT * FROM entrada e WHERE e.indexb_id = ?1 and e.n_fulla = ?2", nativeQuery = true)
    List<Entrada> findEntradaByIndexBIDandNFulla(long indexB_id, int n);

}

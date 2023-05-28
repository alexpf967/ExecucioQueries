package com.example.demo.repositories;

import com.example.demo.classes.IndexB;
import com.example.demo.classes.Taula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexBRepository extends JpaRepository<IndexB, Long> {
    @Query(value = "SELECT * FROM indexb ib WHERE ib.nom_indexb = ?1", nativeQuery = true)
    IndexB findByNomIndexB (String nom_indexb);
}

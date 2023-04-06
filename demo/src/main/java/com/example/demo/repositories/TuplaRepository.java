package com.example.demo.repositories;

import com.example.demo.classes.Tupla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuplaRepository extends JpaRepository<Tupla, Long> {
    List<Tupla> findByAtribut(String atribut);
    @Query(value = "SELECT * FROM tupla t WHERE t.bloc_id = ?1", nativeQuery = true)
    List<Tupla> findByBlocID(long bloc_id);

}

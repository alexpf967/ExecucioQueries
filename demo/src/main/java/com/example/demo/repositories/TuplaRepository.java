package com.example.demo.repositories;

import com.example.demo.tupla.Tupla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TuplaRepository extends JpaRepository<Tupla, Long> {
    List<Tupla> findByAtribut(String atribut);
}

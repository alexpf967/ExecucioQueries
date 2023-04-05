package com.example.demo.repositories;

import com.example.demo.tupla.Tupla;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuplaRepository extends CrudRepository<Tupla, Long> {
    List<Tupla> findById(String atribut);
}

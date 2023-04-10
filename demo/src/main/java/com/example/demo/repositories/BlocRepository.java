package com.example.demo.repositories;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Tupla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
    @Query(value = "SELECT * FROM bloc b JOIN tupla ON b.id = bloc_id WHERE b.id = ?1",
            nativeQuery = true)
    List<Tupla> findTuplas(long blocID);
}

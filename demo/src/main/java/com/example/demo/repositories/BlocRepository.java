package com.example.demo.repositories;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Tupla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
    @Query(value = "SELECT * FROM bloc b WHERE b.taula_id = ?1 ORDER BY b.id", nativeQuery = true)
    List<Bloc> findByTaulaID(long taula_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM bloc b where b.id= ?1", nativeQuery = true)
    void deleteBloc(long id);

}

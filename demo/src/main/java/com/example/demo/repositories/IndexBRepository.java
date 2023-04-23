package com.example.demo.repositories;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.IndexB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexBRepository extends JpaRepository<IndexB, Long> {
}
